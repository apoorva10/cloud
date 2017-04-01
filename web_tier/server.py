import pika
import ssh
import time
#cnt=0
connection = pika.BlockingConnection(pika.ConnectionParameters(
        host='localhost'))

channel = connection.channel()

res=channel.queue_declare(queue='rpc_queue')


def fib(n):
        return ssh.pifft(n)

def on_request(ch, method, props, body):
    n = int(body)

    print(" [.] cloudpi(%s)" % n)
    time.sleep(10)

        #print(res.method.message_count)
    response = str(fib(n))

    ch.basic_publish(exchange='',
                     routing_key=props.reply_to,
                     properties=pika.BasicProperties(correlation_id = \
                                                         props.correlation_id),
                     body=str(response))
    ch.basic_ack(delivery_tag = method.delivery_tag)

channel.basic_qos(prefetch_count=1)
channel.basic_consume(on_request, queue='rpc_queue')

print(" [x] Awaiting RPC requests")
channel.start_consuming()
