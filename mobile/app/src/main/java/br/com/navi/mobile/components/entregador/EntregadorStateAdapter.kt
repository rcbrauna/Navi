package br.com.navi.mobile.components.entregador

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.navi.mobile.components.comprador.fragments.FragEntregas
import br.com.navi.mobile.components.entregador.fragments.FragPedidosEntregador

class EntregadorStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var fragment:ArrayList<Fragment> = arrayListOf(
        FragPedidosEntregador(),
        FragEntregas()
    )

    override fun getItemCount(): Int {
        return fragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragment[position]
    }
}