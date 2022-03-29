package au.com.carsales.basemodule.dependency.baseconcreate.component

import au.com.carsales.basemodule.dependency.baseconcreate.module.BaseConcreteAPIModule
import au.com.carsales.basemodule.dependency.baseconcreate.module.BaseConcreteApplicationModule
import au.com.carsales.basemodule.dependency.baseconcreate.module.BaseConcreteRepositoryModule
import au.com.carsales.basemodule.dependency.component.BaseApplicationComponent
import au.com.carsales.basemodule.tracking.bi.BiTracker
import au.com.carsales.basemodule.tracking.url.UrlTracker
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            BaseConcreteApplicationModule::class,
            BaseConcreteRepositoryModule::class,
            BaseConcreteAPIModule::class]
)
interface BaseConcreteApplicationComponent : BaseApplicationComponent {
    fun inject(biTracker: BiTracker)
    fun inject(urlTracker: UrlTracker)
}
