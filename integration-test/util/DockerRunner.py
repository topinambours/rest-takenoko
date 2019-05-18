#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from subprocess import Popen, PIPE
import docker

class DockerRunner :

    def __init__(self, username, password):
        self.client = docker.from_env()
        self.client.login(username, password)
        self.prepare_takenoko_images()

    def pull_image(self, repo, imageName, tag):
        self.client.images.pull('{}/{}:{}'.format(repo, imageName, tag))

    def prepare_takenoko_images(self):
        print('\033[92mUPDATING IMAGES\033[0m')
        self.pull_image('topinambours', 'takenoko', 'test-server')
        self.pull_image('topinambours', 'takenoko', 'test-client')
        print('\033[92mALL IMAGES ARE UP TO DATE\033[0m')

    def start_server(self, port, gameSize):
        return self.client.containers.run("topinambours/takenoko:test-server", "{} {}".format(port, gameSize), network='host', detach=True)

    def start_server_no_timeout(self, port, gameSize):
        return self.client.containers.run("topinambours/takenoko:test-server", "{} {} true".format(port, gameSize), network='host', detach=True)


    def start_client(self,port, serverPort, clientId):
        return self.client.containers.run('topinambours/takenoko:test-client', '{} http://localhost:{} {}'.format(port, serverPort, clientId), network='host', detach=True)

def check_connection(port):
    app = ['nc', '-vz', 'localhost', str(port)]
    p = Popen(app, stdout=PIPE, stderr=PIPE)
    p.wait()
    (err, out) = p.communicate()
    out = out.decode()
    err = err.decode()
    if ('open' in out) or ('succeeded!' in out):
        return True
    else:
        return False
