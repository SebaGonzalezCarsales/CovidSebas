package au.com.carsales.basemodule.tracking

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MembershipSessionInfo @Inject constructor() {
    var memberTrackingId: String? = null

}