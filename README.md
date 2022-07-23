# shopping_backend
backend integrated in spring-boot 

# API Documentation

### Login Controller

1. /auth : Authenticate API at the time of login
2. /login : API to login


### Item Controller

1. /items : API to fetch all items
2. /items/search : API to fetch all items according to the search values
3. /category: API to fetch all distinct categories
4. /additem : API to add item
5. /updateitem : API to update item
6. /removeitem/:_id : API to remove item



### Mycart Controller

1. /mycart : API to fetch my cart items based on my userid
2. /addtocart : API to add items in my cart
3. /updatemycart: API to update my cart
4. /removefromcart/:_id : API to remove a item from my cart
5. /removecart : API to refresh my cart

### Message Controller

1. /messages : API to send messages to the clients
