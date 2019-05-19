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
    print('\033[92mALL IMAGES ARE UP TO DATE\033[0m\n')


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

def generate_ports(games, startPort):
    out = []
    for game in games :
        out.append((startPort, [port for port in range(startPort + 1, startPort + game[1] + 1)]))
        startPort += game[1] + 1
    return out

def generate_game_id(gameToRun):
    out = []
    i = 0
    for gameSize in gameToRun.keys() :
        for e in range(0, gameToRun[gameSize]) :
            out.append((i, gameSize))
            i+= 1
    return out

def start_server(gameId, port, gameSize,waitToClose):
    print('STARTING NEW GAME ID=',gameId, 'SIZE:',gameSize,'ON PORT:',port,"\n")
    app = ['docker', 'run','-it','--network', 'host',"-p",str(port) +":"+ str(port), 'topinambours/takenoko:test-server',str(port), str(gameSize), 'true']
    if waitToClose == str(True):
        app.append('true')
    return Popen(app, stdout=sys.stdout, stderr=DEVNULL)

def start_client(port, serverPort, clientId):
    print('STARTING NEW CLIENT ID=',clientId, 'PORT:',port,'ON SERVER:','http://localhost:{}'.format(serverPort),"\n")
    app = ['docker', 'run','-it','--network', 'host', 'topinambours/takenoko:test-client',str(port), 'http://localhost:{}'.format(serverPort), str(clientId)]
    return Popen(app, stdout=sys.stdout, stderr=DEVNULL)


def start(gameToRun, ports,waitToClose):
    # gameToRun 	-> [(0, 2), (1,3)]
    #		two games to run id=0:size=2 & id=1:size=3
    #
    # ports 	-> [(8080, [8081, 8082]), [(8083, [8084, 8085, 8086])]]
    #		first member is the server port, rest list of clients ports indexed with gameId
    for e in gameToRun :
        clientId = 0
        processes = []
        port = ports[e[0]][0]
        processes.append(start_server(e[0], ports[e[0]][0], e[1],waitToClose))
        while not check_connection(port):
            time.sleep(1)
        print("\033[92mGAME CREATED, CONNECTING CLIENTS\033[0m".format(e[0]))

        for c_port in ports[e[0]][1]:
            processes.append(start_client(c_port, ports[e[0]][0], clientId))
            clientId += 1
            while not check_connection(c_port):
                time.sleep(1)

        print("\033[92mGAME STARTED, ALL {} CLIENTS CONNECTED\033[0m".format(e[0], len(ports[e[0]][1])),"\n")

        for p in processes :
            if p.poll() == None :
                p.wait()
        print('\033[92mALL {} PROCESSES TERMINATED\033[0m'.format(len(processes)),'\n')

    print('\033[92mEND OF DEMO\033[0m\n')
    sys.exit(0)


GAMES = []

if __name__ == '__main__' :
    parser = argparse.ArgumentParser()
    parser.add_argument('-sp', '--startingPort',default=8080, type=int, help='The port of the first instanciated game. If the first is instanciated with port 8080 and is of size 2, clients will have port 8081, 8082. The next game will get the port 8083.')
    parser.add_argument('-g',type=int,default=2, help='The size of the game to run')
    parser.add_argument('-u',type=str,default="", help='Docker username')
    parser.add_argument('-p',type=str,default="", help='Docker password')
    parser.add_argument('-c','--waitToClose',default="True",type=str,help='Waiting to close at end game')
    args = parser.parse_args()

    gameToRun = {args.g:1}
    GAMES = generate_game_id(gameToRun)
    PORTS = generate_ports(GAMES, args.startingPort)
    docker_login(args.u, args.p)
    update_docker_images()

    if((args.u != "") and (args.p != "")):
        docker_login(args.u, args.p)

    update_docker_images()

    start(GAMES, PORTS,args.waitToClose)



