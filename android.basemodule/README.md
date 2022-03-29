# BaseModule-Android

## Description:

This library provides the base navigation and abstracts to generate: Application, Activity, 
Fragment, Widget classes, Extensions and Utils.

**Content:**

* Abstracts dependencies:
  * BaseApplicationComponent
  * BaseApplicationModule
  * BaseViewModelModule
* Router Definition class
* Tracking (DataView)
* Utils:
    * commonResources (Resource LiveData)
    * dialog
    * facebookManager
    * glide
    * layoutManager
    * sharedPreferences
    * viewModel
    * AsyncTaskManager
    * BaseUtils
    * FileUtils
    * ImageUtils
    * Mapper Interface
    * RecyclerViewMargin
    * VarColumnGridLayoutManager
* Common Extension:
    * BaseModuleExtension
    * ImageLoaderExtension
    * ActivityExtension         
    * LiveDataExtension
    * AppInformationExtension   
    * MenuExtension
    * BottomNavigationExtension 
    * MutableListExtension
    * ButtonExtension           
    * StringExtension
    * ContextExtension          
    * ThrowableExtension
    * FragmentExtension         
    * ToolbarExtension
    * FragmentManagerExtension  
    * ViewExtension
* Common Widgets:
    * adapter
    * cardView
    * empty
    * error
    * loaderRetail
    * scrollView
    * RecyclerViewPager
* Abstract Base Classes: 
    * BaseAppGlideModule
    * BaseModuleApplication
    * BaseModuleBottomActivity
    * BaseModuleNavigationActivity
    * BaseModuleFragment

## Gradle
Root build.gradle

```gradle
allprojects {
    repositories {
        ... 
        maven {
            url "https://jitpack.io"
            credentials { username authToken }
        }
    }
}
```

Add the dependency

Release
 
```gradle
dependencies {
       implementation 'com.github.carsales-PRIVATE:BaseModule-Android:1.4'
}
```

## Samples

### Abstracts Dependencies

**BaseApplicationComponent**

Extends in your ApplicationComponent class, with this saves you from injecting 
the Thread and Application interfaces per module.

```kotlin
@Singleton
@Component
interface YourApplicationComponent : BaseApplicationComponent
```

**BaseApplicationModule**

Extends in your ApplicationModule class, with this saves you the declaration 
of Thread and Application dependencies.

```kotlin
@Module
class YourApplicationModule(application: RetailApplication) : BaseApplicationModule(application)
```

**BaseViewModelModule**

Extends in your ViewModelModule class, with this saves you the declaration of 
ViewModel Factory dependency.

```kotlin
@Module
abstract class YourViewModelModule : BaseViewModelModule()
```

### Abstracts Base Classes

**BaseAppGlideModule**

Provides configuration for image processing with Glide

```xml
<application>
  <meta-data
            android:name="au.com.carsales.basemodule.BaseAppGlideModule"
            android:value="AppGlideModule" />
</application>
```

**BaseModuleApplication**

```kotlin
class YourApplication : BaseModuleApplication() 
    override fun initLifeCycleManager(): BaseModuleLifeCycleManager

```

**BaseModuleBottomActivity**

Provides with a Bottom Navigation, to do so you 
must overwrite the following methods, as shown below.

```kotlin
class YourActivity : BaseModuleBottomActivity() {

    override fun navigationActivityType(): NavigationWithType
    override fun listRootDetailsFragment(): List<Fragment>
    override fun positionRootFragment(): Int
    override fun layoutBottomId(): Int
    override fun rootFrameContainer(): FrameLayout
    override fun detailFrameContainer(): FrameLayout
    override fun containerFrameViewGroup(): ViewGroup
    override fun bottomNavigationView(): BottomNavigationView
    override fun emptyFragment(): Fragment
    override fun layoutId(): Int = layoutBottomId()
}
```

**BaseModuleNavigationActivity**

Provides with a standard structure for your activity.

```kotlin
class YourActivity : BaseModuleNavigationActivity() {
    
    override fun layoutContainerId(): Int 
    override fun frameContainer(): FrameLayout 
    override fun initView(savedInstanceState: Bundle?) 
    override fun savedIntanceOfData(outState: Bundle) 
}
``` 

**BaseModuleFragment**

```kotlin
class SellEntryFragment : BaseModuleFragment() {
    
    override fun isFullScreen(): Boolean 
    override fun layoutId(): Int
    override fun showEmptyView()
    override fun showErrorView(message: String?)
    override fun showLoadingView()
    override fun tagName(): String
}
```

## Libraries

* Glide 

    implementation 'com.github.bumptech.glide:glide:4.7.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.7.1'

* Google Play Service

    api 'com.google.android.gms:play-services-auth:16.0.0'
    api 'com.google.android.gms:play-services-analytics:16.0.1'


* Facebook

    api 'com.facebook.android:facebook-android-sdk:4.34.0'
 
* Support Library 28.0.0-rc02