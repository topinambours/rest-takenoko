import sys
import unittest
from util import wrapper

if __name__ == '__main__':
    wrapper.wrapper.parse_args()
    unittest.main(module=None, argv=sys.argv, verbosity=2)
