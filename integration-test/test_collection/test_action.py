#!/usr/bin/env python3
# -*- coding: utf-8 -*-
from util import DockerRunner
from util import wrapper
import unittest
import time
from itertools import cycle


class TestAction(unittest.TestCase):
    drunner = wrapper.wrapper.args['dockerRunner']

    def setUp(self):
        self.drunner.close_running_containers()

    def test_turn_sequence_2(self):

        server = self.drunner.start_server(8080, 2)

        DockerRunner.wait_for_port(8080)

        self.assertIn('Nouvelle partie pour 2 joueurs instanciée.', DockerRunner.getlog(server))

        # The last player to join is the first to play
        # We need to guaranty that player id@2 is the last to join

        client = self.drunner.start_client(8081, 8080, 1)
        DockerRunner.wait_for_port(8081)
        client2 = self.drunner.start_client(8082, 8080, 2)

        DockerRunner.wait_for_port(8082)
        while DockerRunner.check_connection(8080):
            time.sleep(2)

        self.assertIn("Le joueur 1@localhost:8081 c'est enregistré.", DockerRunner.getlog(server))
        self.assertIn("Le joueur 2@localhost:8082 c'est enregistré.", DockerRunner.getlog(server))

        self.assertIn('GAME ENDED', DockerRunner.getlog(server))
        self.assertIn('GAME ENDED', DockerRunner.getlog(client))
        self.assertIn('GAME ENDED', DockerRunner.getlog(client2))

        log_server = DockerRunner.getlog(server)

        # Last player to join
        list_player = cycle([2, 1])
        expected_player = next(list_player)
        turn_running = False

        for line in log_server:
            if turn_running:
                # No player have to play until current player notified his end turn
                self.assertNotIn("C'est au tour du joueur", line)

            # No one is notifying is end turn if there is no turn playing
            if not turn_running:
                self.assertNotIn("a terminé son tour.", line)

            if "C'est au tour du joueur" in line:
                self.assertIn("C'est au tour du joueur {}".format(expected_player), line)
                turn_running = True

            if turn_running and "a terminé son tour." in line:
                self.assertIn("Le joueur {} a terminé son tour.".format(expected_player), line)
                turn_running = False
                expected_player = next(list_player)

    def test_turn_sequence_3(self):

        server = self.drunner.start_server(8080, 3)

        DockerRunner.wait_for_port(8080)

        self.assertIn('Nouvelle partie pour 3 joueurs instanciée.', DockerRunner.getlog(server))

        # The last player to join is the first to play
        # We need to guaranty that player id@2 is the last to join

        client = self.drunner.start_client(8081, 8080, 1)
        DockerRunner.wait_for_port(8081)
        client2 = self.drunner.start_client(8082, 8080, 2)

        DockerRunner.wait_for_port(8082)

        client3 = self.drunner.start_client(8083, 8080, 3)

        DockerRunner.wait_for_port(8083)
        while DockerRunner.check_connection(8080):
            time.sleep(2)

        self.assertIn("Le joueur 1@localhost:8081 c'est enregistré.", DockerRunner.getlog(server))
        self.assertIn("Le joueur 2@localhost:8082 c'est enregistré.", DockerRunner.getlog(server))
        self.assertIn("Le joueur 3@localhost:8083 c'est enregistré.", DockerRunner.getlog(server))

        self.assertIn('GAME ENDED', DockerRunner.getlog(server))
        self.assertIn('GAME ENDED', DockerRunner.getlog(client))
        self.assertIn('GAME ENDED', DockerRunner.getlog(client2))
        self.assertIn('GAME ENDED', DockerRunner.getlog(client3))

        log_server = DockerRunner.getlog(server)

        # Last player to join
        list_player = cycle([3, 2, 1])
        expected_player = next(list_player)
        turn_running = False

        for line in log_server:
            if turn_running:
                # No player have to play until current player notified his end turn
                self.assertNotIn("C'est au tour du joueur", line)

            # No one is notifying is end turn if there is no turn playing
            if not turn_running:
                self.assertNotIn("a terminé son tour.", line)

            if "C'est au tour du joueur" in line:
                self.assertIn("C'est au tour du joueur {}".format(expected_player), line)
                turn_running = True

            if turn_running and "a terminé son tour." in line:
                self.assertIn("Le joueur {} a terminé son tour.".format(expected_player), line)
                turn_running = False
                expected_player = next(list_player)

    def test_turn_sequence_4(self):

        server = self.drunner.start_server(8080, 4)

        DockerRunner.wait_for_port(8080)

        self.assertIn('Nouvelle partie pour 4 joueurs instanciée.', DockerRunner.getlog(server))

        # The last player to join is the first to play
        # We need to guaranty that player id@2 is the last to join

        client = self.drunner.start_client(8081, 8080, 1)
        DockerRunner.wait_for_port(8081)

        client2 = self.drunner.start_client(8082, 8080, 2)
        DockerRunner.wait_for_port(8082)

        client3 = self.drunner.start_client(8083, 8080, 3)
        DockerRunner.wait_for_port(8083)

        client4 = self.drunner.start_client(8084, 8080, 4)
        DockerRunner.wait_for_port(8083)
        while DockerRunner.check_connection(8080):
            time.sleep(2)

        self.assertIn("Le joueur 1@localhost:8081 c'est enregistré.", DockerRunner.getlog(server))
        self.assertIn("Le joueur 2@localhost:8082 c'est enregistré.", DockerRunner.getlog(server))
        self.assertIn("Le joueur 3@localhost:8083 c'est enregistré.", DockerRunner.getlog(server))
        self.assertIn("Le joueur 4@localhost:8084 c'est enregistré.", DockerRunner.getlog(server))

        self.assertIn('GAME ENDED', DockerRunner.getlog(server))
        self.assertIn('GAME ENDED', DockerRunner.getlog(client))
        self.assertIn('GAME ENDED', DockerRunner.getlog(client2))
        self.assertIn('GAME ENDED', DockerRunner.getlog(client3))
        self.assertIn('GAME ENDED', DockerRunner.getlog(client4))

        log_server = DockerRunner.getlog(server)

        # Last player to join
        list_player = cycle([4, 3, 2, 1])
        expected_player = next(list_player)
        turn_running = False

        for line in log_server:
            if turn_running:
                # No player have to play until current player notified his end turn
                self.assertNotIn("C'est au tour du joueur", line)

            # No one is notifying is end turn if there is no turn playing
            if not turn_running:
                self.assertNotIn("a terminé son tour.", line)

            if "C'est au tour du joueur" in line:
                self.assertIn("C'est au tour du joueur {}".format(expected_player), line)
                turn_running = True

            if turn_running and "a terminé son tour." in line:
                self.assertIn("Le joueur {} a terminé son tour.".format(expected_player), line)
                turn_running = False
                expected_player = next(list_player)
