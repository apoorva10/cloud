import boto.ec2
from subprocess import call
import sys

conn = boto.ec2.connect_to_region("us-west-2")
aws_access_key_id='AKIAI3MKL6KSRVV2SDFA'
aws_secret_access_key='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'
conn = boto.ec2.connect_to_region("us-west-2")

for r in conn.get_all_instances():
	#print r.instances
	r.instances[0].terminate()



