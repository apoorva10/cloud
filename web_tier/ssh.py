import boto.ec2
import sys
from boto.manage.cmdshell import sshclient_from_instance
import time
inst_id=0


def connect():
        conn = boto.ec2.connect_to_region("us-west-2")
        result=conn.run_instances('ami-45c94025',key_name='cloudpi',
        instance_type='t2.micro',
        security_groups=['launch-wizard-1'])
        #print result.id
        for r in conn.get_all_instances():
                if r.id == result.id:
                    break
        print "Instance is getting created and the id is "+r.instances[0].id
        #print r.instances

        while r.instances[0].state not in ('running'):
                time.sleep(5)
                r.instances[0].update()
        time.sleep(60)
        inst_id= str(r.instances[0].id)
        return inst_id


def pifft(n):
        conn = boto.ec2.connect_to_region('us-west-2')
        inst_id=connect()

        instance = conn.get_all_instances(inst_id)[0].instances[0]
        ssh_client = sshclient_from_instance(instance,
                                                 '/home/ubuntu/Python-2.7.12/cloudpi.pem',
                                                 user_name='ec2-user')
        status, stdout, stderr = ssh_client.run('echo '+str(n)+' > "/cloud/input"')
        #print stdout,stderr
        status, stdout, stderr = ssh_client.run('gcc -O6 -ffast-math /home/ec2-user/cloud/pifft.c /home/ec2-user/cloud/fftsg.c -lm -o pi')
        status, stdout, stderr = ssh_client.run('./pi /cloud/input')
        print type(stdout)
        ssh_client.run('echo '+stdout.rstrip('\n')+' > "/cloud/file.txt"')
        s,s1,s2=ssh_client.run('sh /cloud/put.sh')
