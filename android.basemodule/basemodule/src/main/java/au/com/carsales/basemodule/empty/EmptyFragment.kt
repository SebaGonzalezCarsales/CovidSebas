package au.com.carsales.basemodule.empty

import androidx.fragment.app.Fragment
import au.com.carsales.basemodule.BaseModuleFragment
import au.com.carsales.basemodule.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EmptyFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EmptyFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EmptyFragment : BaseModuleFragment() {
    override fun tagName(): String = EmptyFragment::class.java.simpleName

    override fun isFullScreen(): Boolean = false

    override fun layoutId(): Int = R.layout.fragment_empty

    override fun showLoadingView() {
        TODO("Not yet implemented")
    }

    override fun showEmptyView() {
        TODO("Not yet implemented")
    }

    override fun showErrorView(message: String?) {
        TODO("Not yet implemented")
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmptyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = EmptyFragment()

    }
}
