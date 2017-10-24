# weather-alarm-clock
Alarm clock mobile app with weather forecast included.

Name: Danciu Cristian-Mihai

User: dcie0963

## Description
The purpose of this app is to offer the possibility to handle alarms and to display the weather forecast for a specific location.

In order to use the app, the user must authenticate. User specific preferences (for example: set alarms) should have a backup so the user will be able to reinstall the app without losing his saved settings.

A user can have one of the following roles:
* Power user: can create/update/delete alarms
* User: can only view set alarms

## Alarm handling
* Set a new alarm (power user)
* View all set alarms (power user and user)
* Change an existing alarm (power user)
* Remove existing alarms (power user)

## Weather forecast
* When the alarm rings, the app should be able to connect to a weather forecast service and fetch the data
* If an internet connection is not available, the app should present the most recent saved forecast data
* Possibility to display a chart with rain chances and temperatures for the day
* Notify user when the forecast data was updated
