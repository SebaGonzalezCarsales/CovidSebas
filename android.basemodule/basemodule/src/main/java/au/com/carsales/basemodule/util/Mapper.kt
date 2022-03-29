package au.com.carsales.basemodule.util

/**
 * Created by joseignacio on 1/30/18.
 */

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer layers
 *
 * @param <V> the view model input type
 * @param <D> the domain model output type
 */
interface Mapper<out Destination, in Source> {

    fun executeMapping(type: Source?): Destination?

}