package it.polito.did.smartvase.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import it.polito.did.smartvase.R

class IconListFragment : Fragment() {

    val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.fade)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.icon_list_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        //Inizializzazione icone
        val Icons = arrayListOf<PlantIcon>()
        Icons.add(PlantIcon(R.drawable.nficusicon,"Ficus", 50, 25))
        Icons.add(PlantIcon(R.drawable.nficusicon,"Gianni", 50, 25))
        Icons.add(PlantIcon(R.mipmap.ic_launcher,"Franco", 50, 25))
        Icons.add(PlantIcon(R.mipmap.ic_launcher,"DottorCisafrulli", 50, 25))
        Icons.add(PlantIcon(R.mipmap.ic_launcher,"Vlad", 50, 25))
        Icons.add(PlantIcon(R.mipmap.ic_launcher,"Ciruzzo", 50, 25))
        Icons.add(PlantIcon(R.mipmap.ic_launcher,"Vlad", 50, 25))
        Icons.add(PlantIcon(R.mipmap.ic_launcher,"Vlad", 50, 25))
        Icons.add(PlantIcon(R.mipmap.ic_launcher,"Vlad", 50, 25))
        Icons.add(PlantIcon(R.mipmap.ic_launcher,"Vlad", 50, 25))

        //Mostra le voci come lista lineare
        recyclerView?.layoutManager =LinearLayoutManager(this.context)
        //Popola la recyclerView con i dati
        recyclerView?.adapter = IconAdapter(Icons,this)
    }

    fun setMvm(p:PlantIcon){
        if(!viewModel.plantCreated) {
            viewModel.defaultMax = p.defaultMax * .01f //TODO PRENDERE I VALORI DEFAULT dal tipo pianta
            viewModel.defaultMin = p.defaultMin * .01f
            viewModel.plantName = p.type
        }
//      viewModel.plantIcon = resources.getDrawable(p.iconId)
        viewModel.plantIconId = p.iconId
        viewModel.setupSetted = true
        goBack()
    }

    fun goBack(){findNavController().navigate(R.id.action_iconListFragment_to_plantSetup)}
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    // Leave empty do disable back press or
                    // write your code which you want
                    goBack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }
}