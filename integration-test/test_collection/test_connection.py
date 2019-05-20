#!/usr/bin/env python3
# -*- coding: utf-8 -*-
from util import DockerRunner
from util import wrapper
import unittest
import time


class TestDeploy(unittest.TestCase):
    drunner = wrapper.wrapper.args['dockerRunner']

    def setUp(self):
        self.drunner.close_running_containers()

    #
    # The server is running 20s after system call
    def test_deploy_time_gameSize_2(self):
        server = self.drunner.start_server(8080, 2)

        time.sleep(20)
        self.assertTrue(DockerRunner.check_connection(8080))

        log = DockerRunner.getlog(server)

        self.assertIn('Nouvelle partie pour 2 joueurs instanciée.', log)
        server.kill()

    def test_deploy_time_gameSize_3(self):
        server = self.drunner.start_server(8080, 3)

        time.sleep(20)
        self.assertTrue(DockerRunner.check_connection(8080))

        log = DockerRunner.getlog(server)

        self.assertIn('Nouvelle partie pour 3 joueurs instanciée.', log)
        server.kill()

    def test_deploy_time_gameSize_4(self):
        server = self.drunner.start_server(8080, 4)

        time.sleep(20)
        self.assertTrue(DockerRunner.check_connection(8080))

        log = DockerRunner.getlog(server)

        self.assertIn('Nouvelle partie pour 4 joueurs instanciée.', log)
        server.kill()

    #
    # Server must be disconnected after 30 seconds without clients
    def test_server_timeout_2(self):
        server = self.drunner.start_server(8080, 2)

        while not DockerRunner.check_connection(8080):
            time.sleep(1)
        time.sleep(10)
        self.assertIn('Nouvelle partie pour 2 joueurs instanciée.', DockerRunner.getlog(server))

        time.sleep(31)
        log = DockerRunner.getlog(server)

        self.assertIn('No clients after 30 seconds, closing server app', log)
        self.assertFalse(DockerRunner.check_connection(8080))

    #
    # Server must be disconnected after 30 seconds without clients
    def test_server_timeout_3(self):
        server = self.drunner.start_server(8080, 3)

        while not DockerRunner.check_connection(8080):
            time.sleep(1)
        time.sleep(40)
        self.assertIn('Nouvelle partie pour 3 joueurs instanciée.', DockerRunner.getlog(server))

        self.assertIn('No clients after 30 seconds, closing server app', DockerRunner.getlog(server))
        self.assertFalse(DockerRunner.check_connection(8080))

    #
    # Server must be disconnected after 30 seconds without clients
    def test_server_timeout_4(self):
        server = self.drunner.start_server(8080, 4)

        while not DockerRunner.check_connection(8080):
            time.sleep(1)
        time.sleep(40)
        self.assertIn('Nouvelle partie pour 4 joueurs instanciée.', DockerRunner.getlog(server))

        self.assertIn('No clients after 30 seconds, closing server app', DockerRunner.getlog(server))
        self.assertFalse(DockerRunner.check_connection(8080))

    #
    # Game does not start until there is the required number of users
    def test_game_not_starting_until_2_players_timeout(self):
        server = self.drunner.start_server(8080, 2)

        while not DockerRunner.check_connection(8080):
            time.sleep(1)
        time.sleep(10)

        self.assertIn('Nouvelle partie pour 2 joueurs instanciée.', DockerRunner.getlog(server))

        client = self.drunner.start_client(8081, 8080, 1)

        while not DockerRunner.check_connection(8081):
            time.sleep(1)

        time.sleep(10)

        self.assertIn("Le joueur 1@localhost:8081 c'est enregistré.", DockerRunner.getlog(server))

        # game not started
        self.assertNotIn("C'est au tour du joueur", DockerRunner.getlog(server))

        time.sleep(60)
        self.assertFalse(DockerRunner.check_connection(8080))
        self.assertIn("Missing one or more clients, closing server", DockerRunner.getlog(server))

        # Check if client is closed
        self.assertIn("SERVER REQUEST TO CLOSE APPLICATION", DockerRunner.getlog(client))
        self.assertFalse(DockerRunner.check_connection(8081))

    #
    # Game does not start until there is the required number of users
    def test_game_start_when_2_players(self):

        server = self.drunner.start_server_no_timeout(8080, 2)

        DockerRunner.wait_for_port(8080)

        self.assertIn('Nouvelle partie pour 2 joueurs instanciée.', DockerRunner.getlog(server))

        client = self.drunner.start_client(8081, 8080, 1)
        DockerRunner.wait_for_port(8081)
        client2 = self.drunner.start_client(8082, 8080, 2)
        DockerRunner.wait_for_port(8082)

        time.sleep(60)
        # game started
        self.assertIn("C'est au tour du joueur", DockerRunner.getlog(server))
        self.assertIn("Le joueur 1@localhost:8081 c'est enregistré.", DockerRunner.getlog(server))
        self.assertIn("Le joueur 2@localhost:8082 c'est enregistré.", DockerRunner.getlog(server))

        self.assertIn("Notification de jouer reçu", DockerRunner.getlog(client))
        self.assertIn("Notification de jouer reçu", DockerRunner.getlog(client2))

        # game Ended
        self.assertIn("GAME ENDED", DockerRunner.getlog(server))
        self.assertIn("GAME ENDED", DockerRunner.getlog(client))
        self.assertIn("GAME ENDED", DockerRunner.getlog(client2))

    def test_client_connect_server_disconnected(self):
        client = self.drunner.start_client(8081, 8080, 1)
        time.sleep(15)
        self.assertFalse(DockerRunner.check_connection(8081))
        self.assertIn('SERVER DISCONNECTED', DockerRunner.getlog(client))

    #
    # The server is suppose to close the game when one client get disconnected
    # The server must ask to reachable clients to properly close their application before stopping itself
    def test_game_close_after_disconnected_client(self):
        server = self.drunner.start_server(8080, 2)

        DockerRunner.wait_for_port(8080)
        client = self.drunner.start_client(8081, 8080, 1)
        DockerRunner.wait_for_port(8081)
        client2 = self.drunner.start_client(8082, 8080, 2)
        DockerRunner.wait_for_port(8082)

        # Both player have played
        # Let's kill one of these guys

        client2.kill()
        time.sleep(40)

        log_server = DockerRunner.getlog(server)
        log_client = DockerRunner.getlog(client)

        self.assertIn("CLIENT 2 DISCONNECTED", log_server)
        self.assertIn("GAME ENDED DISCONNECTING CLIENTS", log_server)

        self.assertIn("SERVER REQUEST TO CLOSE APPLICATION", log_client)
        self.assertIn("GAME ENDED", log_client)
        self.assertIn("GAME ENDED", log_server)

        # All client / server disconnected
        self.assertFalse(DockerRunner.check_connection(8080))
        self.assertFalse(DockerRunner.check_connection(8081))
        self.assertFalse(DockerRunner.check_connection(8082))
