package au.com.carsales.basemodule.router

import au.com.carsales.basemodule.BaseModuleApplication
import au.com.carsales.basemodule.getAppContext


object InjectHelper {
    private val baseAppContext: BaseModuleApplication get() = getAppContext()
    val iModuleConfig: IModuleConfig = baseAppContext.getModuleConfig()


    fun <T> getInstance(clazz: Class<T>): T? {
        val config = iModuleConfig
        val implementClass = config.getServiceImplementClass(clazz)
        if (implementClass != null) {
            try {
                var ins = config.getServiceInstance(implementClass)
                if (ins == null) {
                    ins = implementClass.newInstance()
                    if (ins != null)
                        config.registerSrvInstance(implementClass, ins)
                }
                return ins
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }
}