import boto.ec2
import sys
from subprocess import call
from boto.manage.cmdshell import sshclient_from_instance
import time
inst_id=0


def connect():
        conn = boto.ec2.connect_to_region("us-west-2")
        result=conn.run_instances('ami-0653da66',key_name='cloudpi',
        instance_type='t2.micro',
        security_groups=['default'])
        #print result.id
        for r in conn.get_all_instances():
                if r.id == result.id:
                    break
        #print r.instances[0].id
        #print r.instances

        while r.instances[0].state not in ('running'):
                time.sleep(5)
                r.instances[0].update()
        time.sleep(40)
        inst_id= str(r.instances[0].public_dns_name)
	allocation=conn.get_all_addresses(filters={'public_ip': '52.32.98.46'})[0].allocation_id
	conn.associate_address(instance_id=r.instances[0].id, allocation_id=allocation)
	conn.reboot_instances([r.instances[0].id])
	time.sleep(30)
        return inst_id
print connect()
