# I Stand With Refugees

## I Stand With Refugees is an Android Application mapping all organisations working with refugees in the UK and Europe

![alt text](https://github.com/sefmiller/IstandWithRefugees/blob/master/art/Screenshot_2018-07-24-16-36-52.jpg)![alt text](https://github.com/sefmiller/IstandWithRefugees/blob/master/art/Screenshot_2018-07-24-17-04-54.png)![alt text](https://github.com/sefmiller/IstandWithRefugees/blob/master/art/1.jpg)
![alt text](https://github.com/sefmiller/IstandWithRefugees/blob/master/art/Screenshot_2018-07-24-17-05-12.jpg)![alt text](https://github.com/sefmiller/IstandWithRefugees/blob/master/art/2.jpg)

![alt text](https://github.com/sefmiller/IstandWithRefugees/blob/master/art/3.jpg)![alt text](https://github.com/sefmiller/IstandWithRefugees/blob/master/art/Screenshot_2018-07-24-17-05-58.jpg)![alt text](https://github.com/sefmiller/IstandWithRefugees/blob/master/art/4.jpg)
![alt text](https://github.com/sefmiller/IstandWithRefugees/blob/master/art/5.jpg)![alt text](https://github.com/sefmiller/IstandWithRefugees/blob/master/art/Screenshot_2018-07-24-17-08-33.jpg)

I Stand With Refugees was developed as a response to working in the Calais Jungle Refugee camp and becoming aware of a need for a mobile information hub which provides quickly accessible information of all charitable organisations working for, and with, refugees and asylum seekers. 

Organisations exist in the UK and overseas which provide a range of services for a refugee such as refugee led NGOs, translation, legal services, collecting & delivering aid, fundraising, family reunification, employment assistance, training, befriending and language learning, to name but a few. There has been a great deal of good will towards the plight of refugees fleeing persecution, poverty and terror and the application intends to give that good will, greater exposure. 

Features
* I Stand With Refugees provides an interactive map which allows users to quickly enter a postcode or their current location and discover what refugee related services are going on locally. 
* For refugees and asylum seekers, it is a pit stop for securing assistance and a welcome on arrival. 
* For those looking to volunteer, you'll find a plethora of opportunities to help out up and down the country and further afield, across Europe. 
* The app allows user to add details of their organisations working in the UK and Europe or and online only aid, such as providing phone credit. 
* Users can add fundraising events such as gigs, auctions, art shows or aid collections.
* Bookmark an Organisation or Fundraiser to come back to later.
* Featuring 750+ Organisations.

###  Dependencies 

The Following modules are used in the app:

CircleImageView https://github.com/hdodenhof/CircleImageView. Used to provide a circular, bordered imageview to contain image files.

ImagePicker https://github.com/Mariovc/ImagePicker. Starts native android Gallery/Photos application. On a user picking an image, it is placed into an ImageView.

ShowcaseView https://github.com/amlcurran/ShowcaseView.  Highlight and showcase specific parts of apps to the user with a distinctive and attractive overlay, for tutorial purposes.

ThreeTenABP https://github.com/JakeWharton/ThreeTenABP. Backported Adaption of the Java 310 date and time API. Provides 

Glide. https://github.com/bumptech/glide. An Image loading framework to cache images, manage memory usage and load images from URLS.

Glide https://github.com/bumptech/glide.  Used to retrieve images from URLS and cache into local memory. Used primarily for its superior automatic image recycling.

ThreeTenABP https://github.com/JakeWharton/ThreeTenABP. Backport of the updated Android Time & Date libraries (JSR 310) to support older Android OS.

Facebook Account kit. Provides a simple login solution, verifying a user by sending a verification code via text to their provided number. As the app does not capture and senitive personal information this simpler approach is preferable to a traditional username/password approach.

Amazon AWS S3. Used to securely store, upload and download data such as voicemails and image files.

Amazon AWS Dynamo. Provides a database instance.

Amazon EC2. Hosts a linux web server. The SQL database tables are accessed through phpMyadmin. Php scripts are used to fetch the data from the database and our encoded into JSON, which is then readable by the application. 


### Building

The project builds with Jcenter. 

## Authors

* **Sefton Howie** 
 

## License

 Copyright 2018 Sefton Howie

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
