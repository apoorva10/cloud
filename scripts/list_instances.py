from boto.ec2.cloudwatch import connect_to_region
import datetime
import boto.ec2
cw = connect_to_region('us-west-2')
instances=[]
instanceid=[]
conn = boto.ec2.connect_to_region("us-west-2")
for r in conn.get_all_instances():
		#print r.instances
		#print r.instances[0].state
		if r.instances[0].state =='running':
			#print r
			instances.extend(r.instances)
			for i in range(0,len(instances)):
				#print instances[i]
				r=str(instances[i])
				instanceid.append(r[9:])
			#print type(instanceid)
				for i in range(0,len(instanceid)):
					res=cw.get_metric_statistics(
							300,
							datetime.datetime.utcnow() - datetime.timedelta(seconds=600),
							datetime.datetime.utcnow(),
							'CPUUtilization',
					   	 	'AWS/EC2',
							'Average',
							dimensions={'InstanceId':instanceid[i]}
				   )
				print r,res

