Обязательная часть выполнена вся. Нюансы: не проверена работа с веб-сокетами (но эндпоинты есть).

# Развёртывание

Сначала запустить необходимые сервисы с помощью Docker Compose, 
затем запустить Gradle задачу bootRun.

# Эндпоинты

## POST /auth/register

Регистрация пользователя. Запрос возвращает JWT токен.

Ожидаемый JSON запроса:

```json
{
  "first_name": "string",
  "last_name": "string",
  "email": "string",
  "nickname": "string",
  "password": "string"
}
```

Ожидаемый JSON ответа:

```json
{
  "access_token": "string",
  "refresh_token": "string"
}
```

## POST /auth/refresh-token

Получение нового токена по рефреш токену. Запрос возвращает JWT токен.

Ожидаемый JSON ответа:

```json
{
  "access_token": "string",
  "refresh_token": "string"
}
```

## POST /auth/authenticate

Логин пользователя. Запрос возвращает JWT токен.

Ожидаемый JSON запроса:

```json
{
  "email": "string",
  "password": "string"
}
```

Ожидаемый JSON ответа:

```json
{
  "access_token": "string",
  "refresh_token": "string"
}
```

## POST /chat/send

Отправка сообщения.

Ожидаемый JSON запроса:

```json
{
  "recipient_id": 0,
  "content": "string"
}
```

Ожидаемый JSON ответа:

```json
{
  "access_token": true
}
```

## GET /chat/{recipientId}

Получение сообщений с пользователем.

Ожидаемый JSON ответа:

```json
{
  "data": [
    {
      "content": "string",
      "sender": "string",
      "recipient": "string",
      "sender_id": 0,
      "recipient_id": 0
    }
  ]
}
```

## POST /user/change-password

Изменение пароля.

Ожидаемый JSON запроса:

```json
{
  "currentPassword": "string",
  "newPassword": "string",
  "confirmationPassword": "string"
}
```

Ожидаемый JSON ответа:

```json
{
  "success": true
}
```

## DELETE /user/delete-self

Удаление пользователя.

Ожидаемый JSON ответа:

```json
{}
```

## PATCH /profile/change-my-info

Изменение информации о профиле.

Ожидаемый JSON запроса:

```json
{
  "firstName": "string",
  "lastName": "string"
}
```

## GET /profile/get-my-info

Получить информацию о текущем пользователе.

Ожидаемый JSON ответа:

```json
{
  "first_name": "string",
  "last_name": "string",
  "id": 0
}
```