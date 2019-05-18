#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import unittest
from util import DockerRunner
import time
from util import wrapper


class TestDeploy(unittest.TestCase):

    USERNAME = wrapper.wrapper.args['u']
    PASSWORD = wrapper.wrapper.args['p']
    drunner = DockerRunner.DockerRunner(USERNAME, PASSWORD)

    #
    # The server is running 10s after system call
    def test_deploy_time_gameSize_2(self):
        server = self.drunner.start_server(1, 8080, 2)

        time.sleep(10)
        self.assertTrue(DockerRunner.check_connection(8080))

        log = server.logs().decode('utf8')

        assert 'Nouvelle partie pour 2 joueurs instanciée.' in log

    def test_deploy_time_gameSize_3(self):
        server = self.drunner.start_server(1, 8080, 3)

        time.sleep(10)
        self.assertTrue(DockerRunner.check_connection(8080))

        log = server.logs().decode('utf8')

        assert 'Nouvelle partie pour 3 joueurs instanciée.' in log

    def test_deploy_time_gameSize_4(self):
        server = self.drunner.start_server(1, 8080, 4)

        time.sleep(10)
        self.assertTrue(DockerRunner.check_connection(8080))

        log = server.logs().decode('utf8')

        assert 'Nouvelle partie pour 4 joueurs instanciée.' in log