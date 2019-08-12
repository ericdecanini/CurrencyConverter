# CurrencyConverter

This is the first of many apps I plan to make to put together the skills I've learned through my blog in a real implementation.

This apps makes use of RxJava and Retrofit to download an API of currencies and update them in a list. The value on the top currency can be edited and all currencies below it will update to show their conversions.

This app does however have a few flaws:

1. There's a noticeable drop in performance every second when the data updates.
2. The view and the presenter are quite tightly coupled
3. Unit and UI Tests are extremely basic

I do however have ideas on how to fix these problems either for this app or for the next apps in this 'series' moving forward

1. A revision of the algorithms used when making updates to the list. Performance drop likely comes from a loop function.
2. Better mastery of MVP may be required, or I could possibly look into using EventBus
3. Better mastery of Espresso is highly required, especially since it's a primary testing framework for just about any Android app
