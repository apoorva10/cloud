from subprocess import call

def ssh_conn(dns_name):
        call(['sh','trial.sh',dns_name])
with open('temp.txt', 'r') as f:
    first_line = f.readline()
print type(first_line)
call(['rm','temp.txt'])
ssh_conn(first_line)
