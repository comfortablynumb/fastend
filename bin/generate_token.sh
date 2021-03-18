#!/usr/bin/env bash

curl -X POST -d 'client_id=fastend-api' -d 'username=user1' -d 'password=somepassword' -d 'grant_type=password' \
  'http://localhost:8080/auth/realms/fastend/protocol/openid-connect/token';