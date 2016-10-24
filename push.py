from pyfcm import FCMNotification

push_service = FCMNotification(api_key="AIzaSyAAypELv1o4Zk6aW1p4kDvYzZSFTBXATkQ")

#message_title = "SWatcher"
message_body = "Movement has just been detected"

result = push_service.notify_topic_subscribers(topic_name="test",message_body=message_body)

print result