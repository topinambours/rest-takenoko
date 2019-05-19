import sys
import argparse
from util import DockerRunner


class UnitTestParser(object):

    def __init__(self):
        self.args = None

    def parse_args(self):
        # Parse optional extra arguments
        parser = argparse.ArgumentParser()
        parser.add_argument('-u', type=str, default='hamlab')
        parser.add_argument('-p', type=str, default='thisisnotapassword')
        ns, args = parser.parse_known_args()
        self.args = vars(ns)

        # Now set the sys.argv to the unittest_args (leaving sys.argv[0] alone)
        sys.argv[1:] = args
        self.args['dockerRunner'] = DockerRunner.DockerRunner(wrapper.args['u'], wrapper.args['p'])


wrapper = UnitTestParser()
