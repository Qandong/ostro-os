'''Verify error returns when try to get default sensor with invalid type trhough api sf_get_default_sensor()'''
import os
from oeqa.utils.helper import get_files_dir
from oeqa.oetest import oeRuntimeTest
from oeqa.utils.ddt import ddt, file_data
@ddt
class TestGetDefaultSensorByInvalidType(oeRuntimeTest):
    '''Verify error returns when give invalid type'''
    @file_data('invalid_sensor_type.json')
    def testGetDefaultSensorByInvalidType(self, value):
        '''Verify error returns when give invalid type'''
        mkdir_path = "mkdir -p /opt/sensor-test/apps"
        (status, output) = self.target.run(mkdir_path)
        copy_to_path = os.path.join(get_files_dir(), 'test_get_default_sensor_by_type')
        (status, output) = self.target.copy_to(copy_to_path, \
"/opt/sensor-test/apps/")
        #run test get error and show it's information
        client_cmd = "/opt/sensor-test/apps/test_get_default_sensor_by_type "\
                     + str(value)
        (status, output) = self.target.run(client_cmd)
        print output
        self.assertEqual(status, 0, msg="Error messages: %s" % output)
