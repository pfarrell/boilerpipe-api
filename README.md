# boilerpipe-api
A JSON Api for Boilerpipe

### routes

|route|supported params|note|
|---|---|---|
|/content|[url, extractor, words]|retreives article content|
|/images|[url, extractor, words]|extracts images from web page|

### query string parameters

|name|value|
|---|---|
|url|url to process|
|extractor|[article, canola, everything, largest, mink, numwords, sentences,  default]|
|words|number of words... only used in "mink" extractor|

