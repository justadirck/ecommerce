# angular

```bash
openssl genrsa -des3 -out rootCA.key 2048
```

```bash
openssl req -x509 -new -nodes -key rootCA.key -sha256 -days 1024 -out rootCA.pem
```

```bash
openssl pkcs12 -export -inkey ./rootCA.key -in ./sample.crt -out ./sample.p12
```

```bash
openssl req -new -sha256 -nodes -out server.csr -newkey rsa:2048 -keyout server.key -config <( cat server.csr.cnf )
```

```bash
openssl x509 -req -in server.csr -CA rootCA.pem -CAkey rootCA.key -CAcreateserial -out server.crt -days 500 -sha256 -extfile v3.ext
```

```bash
brave://settings/security
brave://flags/#allow-insecure-localhost
```

```json
"serve": {
    "builder": "@angular-devkit/build-angular:dev-server",
    "options": {
        "browserTarget": "angular:build",
        "ssl": true,
        "sslKey": "ssl/localhost/ecommerce.key",
        "sslCert": "ssl/localhost/ecommerce.crt"
    }
}
```
