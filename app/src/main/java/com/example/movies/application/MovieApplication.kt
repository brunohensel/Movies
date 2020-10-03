package com.example.movies.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**to add a container that is attached to the app's lifecycle,
 * we need to annotate the Application class with @HiltAndroidApp
 *
 * @HiltAndroidApp triggers Hilt's code generation including a base class for your application
 * that can use dependency injection. The application container is the parent container of the app,
 * which means that other containers can access the dependencies that it provides.*/
@HiltAndroidApp
class MovieApplication : Application() {

}