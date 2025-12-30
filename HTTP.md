---

# HTTP/1.1 Basics — From Concepts to Raw Requests with Telnet

This guide teaches the **fundamentals of HTTP/1.1** by first explaining *why* HTTP is structured the way it is, and then showing *exactly how* those concepts appear in raw HTTP requests using **telnet**.

---

## 1. How to Think About an HTTP Request

An HTTP request is made of **clearly separated parts**, each solving a different problem.

### Request components

* **Path** → which resource
* **Query parameters** → how the resource is filtered or shaped
* **Request headers** → metadata and context
* **Body** (optional) → data being sent

### Response components

* **Status code** → result of the request
* **Response headers** → metadata about the response
* **Body** (optional) → returned data

This separation is what makes HTTP simple, flexible, and scalable.

---

## 2. Why Do We Need a Path?

The **path** answers:

> *Which resource am I requesting?*

A single server can expose thousands of resources.

### Examples

```text
/
 /products
 /products/123
 /inventory/ABC123
```

Think of the path as:

> a function or route name on the server

Without paths:

* All requests would go to the same logic
* APIs would not scale or remain readable

---

## 3. Why Do We Need Query Parameters?

Query parameters answer:

> *How should this resource be filtered, searched, or customized?*

They refine the request **without changing the resource identity**.

### Examples

```text
/products?limit=50
/products?supplier=HIT
/inventory?updated_since=2025-01-01
```

Rule of thumb:

* **Paths identify**
* **Parameters refine**

---

## 4. Why Do We Need Request Headers?

Request headers provide **context**, not data.

They answer:

* Who is making the request?
* How is the request authenticated?
* What format is expected?
* What format is being sent?

### Common Request Headers

| Header          | Purpose                 |
| --------------- | ----------------------- |
| `Host`          | Virtual host routing    |
| `Authorization` | Authentication          |
| `Accept`        | Desired response format |
| `Content-Type`  | Format of request body  |
| `User-Agent`    | Client identity         |

Headers allow the same path to behave differently without changing URLs.

---

## 5. Why Do We Need Response Headers?

Response headers describe **what the server did** with your request.

They enable:

* Caching
* Debugging
* Observability
* Versioning

### Common Response Headers

| Header           | Meaning          |
| ---------------- | ---------------- |
| `Content-Type`   | Response format  |
| `Content-Length` | Payload size     |
| `Cache-Control`  | Caching rules    |
| `ETag`           | Resource version |
| `Date`           | Server timestamp |

---

## 6. Why Do We Need a Body?

The **body** carries the actual **data payload**.

A body is typically used when:

* Creating resources (`POST`)
* Updating resources (`PUT`, `PATCH`)

Important:

* HTTP does not care about body format
* Meaning comes from `Content-Type`
* JSON, XML, Protobuf are all valid

---

## 7. Connecting with Telnet

Now let’s see these concepts **directly on the wire**.

Use a domain that supports plain HTTP for learning:

```bash
telnet neverssl.com 80
```

Once connected, you manually type HTTP requests.

---

## 8. Your First HTTP Request (Root)

### Request

```http
GET / HTTP/1.1
Host: neverssl.com

```

Notes:

* `GET` is the HTTP verb
* `/` is the path
* `Host` is required in HTTP/1.1
* The blank line ends the headers

---

## 9. Requesting a Path

Now target a specific resource.

```http
GET /online HTTP/1.1
Host: neverssl.com

```

You’ve now:

* Changed only the **path**
* Hit different server logic
* Kept everything else the same

---

## 10. Adding Request Headers

Headers refine behavior without changing the path.

```http
GET /online HTTP/1.1
Host: neverssl.com
Accept: text/html

```

This tells the server what format you prefer.

---

## 11. Adding Query Parameters

Parameters live in the URL.

```http
GET /online?test=true HTTP/1.1
Host: neverssl.com

```

You are now:

* Requesting the same resource
* With different behavior

---

## 12. HTTP Verbs and CRUD

HTTP verbs express **intent**.

| Verb   | CRUD   | Meaning             |
| ------ | ------ | ------------------- |
| GET    | Read   | Retrieve data       |
| POST   | Create | Create new resource |
| PUT    | Update | Replace resource    |
| PATCH  | Update | Partial update      |
| DELETE | Delete | Remove resource     |

---

### Example: POST with Body

```http
POST /submit HTTP/1.1
Host: neverssl.com
Content-Type: application/json
Content-Length: 42

{"name":"Sample","value":123}
```

Key points:

* Body starts after blank line
* `Content-Type` explains format
* `Content-Length` tells server how much to read

---

## 13. How SOAP Uses HTTP

SOAP is **not a transport protocol**.
It uses HTTP as a carrier.

SOAP typically:

* Uses **POST**
* Sends XML in the body
* Encodes intent inside the XML
* Does not rely on HTTP verbs

---

## 14. SOAP Example (Inventory Request)

### HTTP Layer

```http
POST /InventoryService HTTP/1.1
Host: soap.example.com
Content-Type: text/xml; charset=utf-8
SOAPAction: "getInventory"

```

### SOAP Body

```xml
<Envelope>
  <Body>
    <getInventory>
      <productId>ABC123</productId>
    </getInventory>
  </Body>
</Envelope>
```

HTTP sees:

* POST
* Headers
* Body

SOAP defines:

* Operation
* Parameters
* Meaning

---

## 15. Why This Matters

Understanding raw HTTP:

* Demystifies REST
* Explains SOAP’s limitations
* Makes debugging much easier
* Helps you design better APIs

Once you understand this document, tools like:

* `curl`
* Postman
* FastAPI
* Django
* SOAP clients

…are just **automation on top of plain text**.

---
