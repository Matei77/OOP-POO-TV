**Name: Ionescu Matei-Ștefan**  
**Group: 323CAb**

# OOP Homework #2 - POO TV #

## Summary: ##

**POO TV** implements the core backend mechanics of platforms like HBO GO 
and Netflix using OOP concepts. The application receives a list of command
from a file in _json_ format and generates a new _json_ file with the
required output for each command.

## Implementation: ##

The core of the application is represented by the `PlatformEngine` class.
This  class has been designed using the **Singleton Design Pattern** and acts 
as a database to hold information about the movies and users on the platform,
as well as the current user, the movies the current user can see and the
current page the user is located in.

### User ###
For each user the platform holds information about his credentials, his 
purchased, watched, liked and rated movies, the type of his account, 
the number of free premium movies he has available, his tokens and his balance.

### Movie ###
For each movie the platform holds information about the name, the year, the 
duration, the genres, the actors, the countries banned in, the number of 
likes and the ratings.

### Page Structure ###
Each page has specific actions that can only happen on that page. In my
implementation each page represents a class that implements the _Page_
interface and is created using the **Factory Design Pattern**. Each page class
overrides the required methods from the _Page_ interface. The default output
for each method in the interface is the error message.

### Actions ###
When the `runEngine` method is called movies and users databases will be 
updated with the information contained in the input _json_ file and the actions 
received will be executed. The output will be shown in a new _json_ file called
_results.out_.

The following actions can be executed:

1. **changePage**

    _This action changes the page the user is located on._

    When the _changePage_ action is given, the `changePage` function will be 
   called from the **currentPage**. If the page the user is trying to access is
   invalid the platform will output an error. Otherwise, the page will change
   to the desired one.

2. **login**

    _This action logs in a user on the platform into his account._

    When the _login_ action is given, the `login` function will be called 
   from the **currentPage**. If the user is not located on the _loginPage_ the
   platform will output an error. Otherwise, if the user has an account and 
   his credentials match he will be logged in and page will change to the 
   _loggedInHomepage_.

3. **register**

    _This action registers a new user on the platform._

   When the _register_ action is given, the `register` function will be called
   from the **currentPage**. If the user in not located on the 
   _registerPage_ the platform will output an error. Otherwise, if the user 
   does not already have an account on the platform, he will be registered 
   and logged in and the page will change to the _loggedInHomepage_.

4. **logout**

    _This action logs out a user from the platform._

   When the _logout_ action is given, the `logout` function will be called
   from the **currentPage**. If the user is not logged in the platform will 
   output an error for this action. Otherwise, the user will be logged out 
   and the page will change to the _loggedOutHomepage_.

5. **search**

    _This action searches for movies starting with a certain string._

   When the _search_ action is given, the `search` function will be called
   from the **currentPage**. If the user in not on the _moviesPage_ the 
   platform will output an error. Otherwise, the list of movies seen by the 
   user will change to only contain the movies starting with the selected 
   string. The platform will output the results.

6. **filter**

    _This action filters the movies seen by the user on the platform._

   When the _filter_ action is given, the `filter` function will be called
   from the **currentPage**. If the user in not on the _moviesPage_ the
   platform will output an error. Otherwise, the list of movies seen by the
   user will change to only contain the movies that match the filter 
   criteria. The results will be sorted by rating and/or duration if requested. 
   The platform will output the results.

7. **purchase**

    _This action allow a user to purchase a selected movie._

   When the _purchase_ action is given, the `purchase` function will be called
   from the **currentPage**. If the user is not located on the 
   _seeDetailsPage_ of the selected movie the action will result in an error.
   Otherwise, the movie will be added to the user's purchased movies array 
   and the user's number of tokens/number of free premium movies will be 
   updated. The platform will output the results.
    
8. **watch**

   _This action allow a user to watch a selected movie._

   When the _watch_ action is given, the `watch` function will be called
   from the **currentPage**. If the user is not located on the
   _seeDetailsPage_ of the selected movie and the movie was not purchased 
   previously the action will result in an error. Otherwise, the movie will 
   be added to the user's watched movies array. The platform will output the 
   results.

9. **like**

   _This action allow a user to like a selected movie._

   When the _like_ action is given, the `like` function will be called
   from the **currentPage**. If the user is not located on the
   _seeDetailsPage_ of the selected movie and the movie was not purchased 
   and watched previously the action will result in an error. Otherwise, the
   movie will be added to the user's liked movies array and the movie's 
   number of likes will be updated. The platform will output the results.

10. **rate**

    _This action allow a user to rate a selected movie._

    When the _rate_ action is given, the `rate` function will be called
    from the **currentPage**. If the user is not located on the
    _seeDetailsPage_ of the selected movie and the movie was not purchased
    and watched previously the action will result in an error. Otherwise, the
    movie will be added to the user's rated movies array and the movie's
    rating will be updated. The platform will output the results.

11. **buyPremium**

    _This action allow a user to buy the Premium Subscription for his account._

    When the _buyPremium_ action is given, the `buyPremium` function will be 
    called from the **currentPage**. If the action does not happen on the 
    _upgradePage_ or the user already has a premium account or does not have
    enough tokens to purchase the subscription, an error will occur. 
    Otherwise, the user's number of tokens and the accountType will be updated.

12. **buyTokens**

    _This action allow a user to buy tokens using the points in his balance._

    When the _buyTokens_ action is given, the `buyTokens` function will be 
    called from the **currentPage**. If the action does not happen on the 
    _upgradePage_ or the user does not have enough points in his balance to 
    buy the selected number of tokens an error will occur. Otherwise, the 
    user's number of tokens and balance will be updated.

