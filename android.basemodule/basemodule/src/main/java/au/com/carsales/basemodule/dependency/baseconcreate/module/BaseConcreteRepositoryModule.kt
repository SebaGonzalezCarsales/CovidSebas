package au.com.carsales.basemodule.dependency.baseconcreate.module

import au.com.carsales.basemodule.api.data.baseconcrete.repository.BaseConcreteImpl
import au.com.carsales.basemodule.api.data.dataStoreFactory.BaseConcreteDataRepository
import au.com.carsales.basemodule.api.data.dataStoreFactory.repository.BaseConcreteRemote
import au.com.carsales.basemodule.api.domain.repository.IBaseConcreteDataRepository
import dagger.Binds
import dagger.Module


@Module
internal abstract class BaseConcreteRepositoryModule {


    @Binds
    abstract fun bindBaseConcreteRemote(remote: BaseConcreteImpl): BaseConcreteRemote

    @Binds
    abstract fun bindBaseConcreteRepository(repository: BaseConcreteDataRepository): IBaseConcreteDataRepository

}