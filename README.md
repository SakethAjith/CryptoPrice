# CryptoPrice
A simple project to get real time price of numerous crypto currencies
A project I made to obtain the latest price of as many crypto coins as possible, which turned out to be 10,000+.
Crypto Price gathers prices from coin gecko, in order to store price data, at that instant.
In order to store large amounts of data, including the current price as well as the price history,
I arrived at InfluxDB as the perfect solution.
A time series NoSQL database, storing data in shard groups along with the time stamp, and providing a retention policy to delete outdated data.
