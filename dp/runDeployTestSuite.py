#!/usr/bin/env python3

import argparse
import sys
import time
from subprocess import Popen, PIPE, DEVNULL


def docker_login(username, password):
	pullCmd = ['docker', 'login', '-u', username, '-p', password]
	pullProcess = Popen(pullCmd)
	code = pullProcess.wait()
	if (code != 0):
		sys.exit(1)

def pull_docker_image(repo,imageName, tag):
	pullCmd = ['docker', 'pull', '{}/{}:{}'.format(repo,imageName, tag)]
	pullProcess = Popen(pullCmd)
	code = pullProcess.wait()
	if (code != 0):
		sys.exit(1)

def update_docker_images():
	print('\033[92mUPDATING IMAGES\033[0m')
	pull_docker_image('topinambours', 'takenoko', 'latest-server')
	pull_docker_image('topinambours', 'takenoko', 'latest-client')
	print('\033[92mALL IMAGES ARE UP TO DATE\033[0m')

def generate_game_id(gameToRun):
	out = []
	i = 0
	for gameSize in gameToRun.keys() :
		for e in range(0, gameToRun[gameSize]) :
			out.append((i, gameSize))
			i+= 1
	return out

def generate_ports(games, startPort):
	out = []
	for game in games :
		out.append((startPort, [port for port in range(startPort + 1, startPort + game[1] + 1)]))
		startPort += game[1] + 1
	return out

def check_connection(port):
	app = ['nc', '-vz', 'localhost' ,str(port)]
	p = Popen(app, stdout=PIPE, stderr=PIPE)
	p.wait()
	(out, err) = p.communicate()
	out = out.decode()
	err = err.decode()
	if ('open' in err) or ('succeeded!' in err) :
		return True
	else :
		return False

def start_server(gameId, port, gameSize):
	print('STARTING NEW GAME ID=',gameId, 'SIZE:',gameSize,'ON PORT:',port)
	app = ['docker', 'run','--network', 'host', 'topinambours/takenoko:latest-server',str(port), str(gameSize)]
	return Popen(app, stdout=DEVNULL, stderr=DEVNULL)

def start_client(port, serverPort, clientId):
	print('STARTING NEW CLIENT ID=',clientId, 'PORT:',port,'ON SERVER:','http://localhost:{}'.format(serverPort))
	app = ['docker', 'run','--network', 'host', 'topinambours/takenoko:latest-client',str(port), 'http://localhost:{}'.format(serverPort), str(clientId)]
	return Popen(app, stdout=DEVNULL, stderr=DEVNULL)

def start_sequential(gameToRun, ports):
	# gameToRun 	-> [(0, 2), (1,3)] 
	#		two games to run id=0:size=2 & id=1:size=3
	#
	# ports 	-> [(8080, [8081, 8082]), [(8083, [8084, 8085, 8086])]]
	#		first member is the server port, rest list of clients ports indexed with gameId
	for e in gameToRun :
		clientId = 0
		processes = []
		port = ports[e[0]][0]
		processes.append(start_server(e[0], ports[e[0]][0], e[1]))
		while not check_connection(port):
			time.sleep(1)
		print("\033[92mGAME ID={} CREATED, CONNECTING CLIENTS\033[0m".format(e[0]))
		
		for c_port in ports[e[0]][1]:
			processes.append(start_client(c_port, ports[e[0]][0], clientId))
			clientId += 1
			while not check_connection(c_port):
				time.sleep(1)

		print("\033[92mGAME ID={} STARTED, ALL {} CLIENTS CONNECTED\033[0m".format(e[0], len(ports[e[0]][1])))

		for p in processes :
			if p.poll() == None :
				p.wait()
		print('\033[92mALL {} PROCESSES TERMINATED\033[0m'.format(len(processes)))
		codes = [p.poll() for p in processes]
		for code in codes :
			if code != 0 :
				print('\033[91mTEST FAIL\033[0m')
				sys.exit(-1)
		print('\033[92m[PASS] GAME {}\033[0m'.format(e[0]))
	print('\033[92mALL CLIENTS & GAMES CLOSED PROPERLY TEST PASS\033[0m')
	sys.exit(0)

def start_all_servers_thread(gameToRun, ports,maxThread):
	# gameToRun 	-> [(0, 2), (1,3)] 
	#		two games to run id=0:size=2 & id=1:size=3
	#
	# ports 	-> [(8080, [8081, 8082]), [(8083, [8084, 8085, 8086])]]
	#		first member is the server port, rest list of clients ports indexed with gameId
	processes = []

	cpy = gameToRun
	cpy_port = ports
	while len(cpy) > 0 :
		job = cpy[:maxThread]
		portsJob = cpy_port[:maxThread]
		cpy = cpy[maxThread:]
		cpy_port = cpy_port[maxThread:]
		for game in job :
			processes.append(start_server(game[0], ports[game[0]][0], game[1]))
		opened = 0
		while True :
			server_status = [check_connection(port[0]) for port in portsJob]
			server_status_general = [check_connection(port[0]) for port in ports]
			if not False in server_status :
				print(server_status_general.count(True), '/' , len(gameToRun), "GAME CREATED")
				break
			time.sleep(2)
	print("\033[92mALL GAME STARTED, CONNECTING CLIENTS\033[0m")
	count = 0
	AllClientPort = []
	for l_port in ports:
		for port in l_port[1]:
			AllClientPort.append(port)
	clientId = 0
	for i in range(0, len(gameToRun)):
		for clientPort in ports[i][1][:len(ports[i][1])-1]:
			p = start_client(clientPort, ports[i][0], clientId)
			clientId += 1
			processes.append(p)
		
		while True :
			server_status = [check_connection(port) for port in ports[i][1][:len(ports[i][1])-1]]
			if not False in server_status :
				count += len(ports[i][1][:len(ports[i][1])-1])
				print(count, '/' , len(AllClientPort), "CLIENT INSTANCIATED")
				break
			time.sleep(2)

	
	# Second loop that add the final player at the game (game start)
	for i in range(0, len(gameToRun)):
		for clientPort in ports[i][1][len(ports[i][1])-1:]:
			p = start_client(clientPort, ports[i][0], clientId)
			clientId += 1
			processes.append(p)
		
		while True :
			server_status = [check_connection(port) for port in ports[i][1][len(ports[i][1])-1:]]
			if not False in server_status :
				count += len(ports[i][1][len(ports[i][1])-1:])
				print(count, '/' , len(AllClientPort), "CLIENT INSTANCIATED")
				break
			time.sleep(2)
	
	print('\033[92mGAMES RUNNING\033[0m')
	# Keep this at the end, python will wait for all subprocess to terminate
	for p in processes :
		if p.poll() == None :
			p.wait()
	print('\033[92mALL {} PROCESSES TERMINATED\033[0m'.format(len(processes)))
	codes = [p.poll() for p in processes]
	for e in codes :
		if e != 0 :
			print('\033[91mTEST FAIL\033[0m')
			sys.exit(-1)
	print('\033[92mALL CLIENTS & GAMES CLOSED PROPERLY TEST PASS\033[0m')
	sys.exit(0)
GAMES = []

if __name__ == '__main__' :
	parser = argparse.ArgumentParser()
	parser.add_argument('-m', '--mode',type=str, choices=['sequential', 'parallel'], help='Test mode to use \'sequential\' or \'parallel\'')
	parser.add_argument('-sp', '--startingPort',default=8080, type=int, help='The port of the first instanciated game. If the first is instanciated with port 8080 and is of size 2, clients will have port 8081, 8082. The next game will get the port 8083.')
	parser.add_argument('-g2',type=int,default=1, help='The number of game of size 2 to run (both server and clients)')
	parser.add_argument('-g3',type=int,default=1, help='The number of game of size 3 to run (both server and clients)')
	parser.add_argument('-g4',type=int,default=1, help='The number of game of size 4 to run (both server and clients)')
	parser.add_argument('-u',type=str,default=1, help='Docker username')
	parser.add_argument('-p',type=str,default=1, help='Docker password')
	args = parser.parse_args()
	
	gameToRun = {2:args.g2, 3:args.g3, 4:args.g4}
	GAMES = generate_game_id(gameToRun)
	PORTS = generate_ports(GAMES, args.startingPort)
	docker_login(args.u, args.p)
	update_docker_images()

	if (args.mode == 'parallel'):
		start_all_servers_thread(GAMES, PORTS,2)
	else :
		start_sequential(GAMES, PORTS)

	

