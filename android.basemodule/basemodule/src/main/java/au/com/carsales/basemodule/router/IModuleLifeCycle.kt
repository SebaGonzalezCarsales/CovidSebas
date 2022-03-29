package au.com.carsales.basemodule.router

interface IModuleLifeCycle {
  fun onCreate(config: IModuleConfig)
  fun onDestroy()
}