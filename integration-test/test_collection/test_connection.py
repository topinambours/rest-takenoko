#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import unittest

import requests

from util import DockerRunner
import time
from util import wrapper


def getlog(container):
    return container.logs().decode('utf8')


class TestDeploy(unittest.TestCase):
    USERNAME = wrapper.wrapper.args['u']
    PASSWORD = wrapper.wrapper.args['p']
    drunner = DockerRunner.DockerRunner(USERNAME, PASSWORD)

    #
    # The server is running 20s after system call
    def test_deploy_time_gameSize_2(self):
        server = self.drunner.start_server(8080, 2)

        time.sleep(20)
        self.assertTrue(DockerRunner.check_connection(8080))

        log = getlog(server)

        self.assertIn('Nouvelle partie pour 2 joueurs instanciée.', log)
        server.kill()

    def test_deploy_time_gameSize_3(self):
        server = self.drunner.start_server(8080, 3)

        time.sleep(20)
        self.assertTrue(DockerRunner.check_connection(8080))

        log = getlog(server)

        self.assertIn('Nouvelle partie pour 3 joueurs instanciée.', log)
        server.kill()

    def test_deploy_time_gameSize_4(self):
        server = self.drunner.start_server(8080, 4)

        time.sleep(20)
        self.assertTrue(DockerRunner.check_connection(8080))

        log = getlog(server)

        self.assertIn('Nouvelle partie pour 4 joueurs instanciée.', log)
        server.kill()

    #
    # Server must be disconnected after 30 seconds without clients
    def test_server_timeout_2(self):
        server = self.drunner.start_server(8080, 2)

        while not DockerRunner.check_connection(8080):
            time.sleep(1)
        time.sleep(10)
        self.assertIn('Nouvelle partie pour 2 joueurs instanciée.', getlog(server))

        time.sleep(31)
        log = getlog(server)

        self.assertIn('No clients after 30 seconds, closing server app', log)
        self.assertFalse(DockerRunner.check_connection(8080))

    #
    # Server must be disconnected after 30 seconds without clients
    def test_server_timeout_3(self):
        server = self.drunner.start_server(8080, 3)

        while not DockerRunner.check_connection(8080):
            time.sleep(1)
        time.sleep(40)
        self.assertIn('Nouvelle partie pour 3 joueurs instanciée.', getlog(server))

        self.assertIn('No clients after 30 seconds, closing server app', getlog(server))
        self.assertFalse(DockerRunner.check_connection(8080))

    #
    # Server must be disconnected after 30 seconds without clients
    def test_server_timeout_4(self):
        server = self.drunner.start_server(8080, 4)

        while not DockerRunner.check_connection(8080):
            time.sleep(1)
        time.sleep(40)
        self.assertIn('Nouvelle partie pour 4 joueurs instanciée.', getlog(server))

        self.assertIn('No clients after 30 seconds, closing server app', getlog(server))
        self.assertFalse(DockerRunner.check_connection(8080))

    #
    # Game does not start until there is the required number of users
    def test_game_not_starting_until_2_players_timeout(self):
        server = self.drunner.start_server(8080, 2)

        while not DockerRunner.check_connection(8080):
            time.sleep(1)
        time.sleep(10)

        self.assertIn('Nouvelle partie pour 2 joueurs instanciée.', getlog(server))

        client = self.drunner.start_client(8081, 8080, 1)

        while not DockerRunner.check_connection(8081) :
            time.sleep(1)

        time.sleep(10)

        self.assertIn("Le joueur 1@localhost:8081 c'est enregistré.", getlog(server))

        # game not started
        self.assertNotIn("C'est au tour du joueur", getlog(server))

        time.sleep(30)
        self.assertFalse(DockerRunner.check_connection(8080))
        self.assertIn("Missing one or more clients, closing server", getlog(server))

        # Check if client is closed
        self.assertIn("SERVER REQUEST TO CLOSE APPLICATION", getlog(client))
        self.assertFalse(DockerRunner.check_connection(8081))

    #
    # Game does not start until there is the required number of users
    def test_game_start_when_2_players(self):
        server = self.drunner.start_server_no_timeout(8080, 2)

        while not DockerRunner.check_connection(8080):
            time.sleep(1)
        time.sleep(10)

        self.assertIn('Nouvelle partie pour 2 joueurs instanciée.', getlog(server))

        client = self.drunner.start_client(8081, 8080, 1)
        client2 = self.drunner.start_client(8082, 8080, 2)

        while False in [DockerRunner.check_connection(8081), DockerRunner.check_connection(8082)]:
            time.sleep(5)

        time.sleep(40)
        # game started
        self.assertIn("C'est au tour du joueur", getlog(server))
        self.assertIn("Le joueur 1@localhost:8081 c'est enregistré.", getlog(server))
        self.assertIn("Le joueur 2@localhost:8082 c'est enregistré.", getlog(server))

        self.assertIn("Notification de jouer reçu", getlog(client))
        self.assertIn("Notification de jouer reçu", getlog(client2))

        # game Ended
        self.assertIn("GAME ENDED", getlog(server))
        self.assertIn("GAME ENDED", getlog(client))
        self.assertIn("GAME ENDED", getlog(client2))

    def test_client_connect_server_disconnected(self):
        client = self.drunner.start_client(8081, 8080, 1)
        time.sleep(15)
        self.assertFalse(DockerRunner.check_connection(8081))
        self.assertIn('SERVER DISCONNECTED', getlog(client))

