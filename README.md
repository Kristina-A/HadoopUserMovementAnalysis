# Hadoop application for analyzing user movements
Analyses are based on two type of files:

![data](https://user-images.githubusercontent.com/37186937/75810482-f4abef00-5d8a-11ea-93c0-b52569d31230.PNG)

In files of this type the following information are stored:
1. location's latitude
2. location's longitude
3. 0
4. altitude in feet
5. number of days since 30.12.1899.
6. date
7. time

![labels](https://user-images.githubusercontent.com/37186937/75810826-9af7f480-5d8b-11ea-9526-924ac69c6be1.PNG)

In files of this type information about used transportation modes are stored.

## Analyses
- number of rows registered at a sprecific location within a certain time range which are related to any type of driving
- min, max average altitude at which user has walked
- top N rows with highest altitude at which user has ridden a bike within a certain time range
- time spent outside for each day
- different locations which user has visited within a certain time range
