package br.com.navi.mobile.components.vendedor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import br.com.navi.mobile.R
import br.com.navi.mobile.components.login.codUser
import br.com.navi.mobile.models.Pedido
import br.com.navi.mobile.services.PedidoService
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_vendedor_frag_pedidos.*
import kotlinx.android.synthetic.main.activity_vendedor_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainVendedor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendedor_main)

        grupoDeFragments()

        icon_perfil.setOnClickListener {
            startActivity(Intent(this, PerfilVendedor::class.java))
        }
    }

    private fun grupoDeFragments(){
        var viewPager: ViewPager2 = findViewById(R.id.viewpager)
        var adapter = VendedorStateAdapter(supportFragmentManager,lifecycle)
        viewPager.adapter = adapter

        var tabLayout: TabLayout = findViewById(R.id.tablayout)
        var names:ArrayList<String> = arrayListOf(getString(R.string.nav_pedidos), getString(R.string.nav_entregadores))
        TabLayoutMediator(tabLayout,viewPager){tab,position ->
            tab.text = names[position]
        }.attach()
    }

    fun showFormPedido(component: View) {
        if (rl_criar_pedido.visibility == View.GONE){
            rl_criar_pedido.visibility = View.VISIBLE
            sv_pedidos.visibility = View.GONE
            bt_add_pedido.visibility = View.GONE
        } else {
            rl_criar_pedido.visibility = View.GONE
            sv_pedidos.visibility = View.VISIBLE
            bt_add_pedido.visibility = View.VISIBLE
        }
    }

    fun createdPedido(component: View){
        val retrofit = Retrofit.Builder()
                .baseUrl("https://navi--api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val requestsPedido = retrofit.create(PedidoService::class.java)
        val newPedido = Pedido(
                null,
                numeroDoPedido = et_nr_pedido.text.toString(),
                descricao = et_pedido_descricao.text.toString(),
                anotacoes = et_pedido_anotacao.text.toString(),
                preco = et_pedido_preco.text.toString(),
                "Pedido Registrado",
                null,
                null,
                null
        )
        val callNewPedido = requestsPedido.createPedido(newPedido, codUser, et_pedido_cpf.text.toString())
        
        callNewPedido.enqueue(object : Callback<Pedido> {
            override fun onResponse(call: Call<Pedido>, response: Response<Pedido>) {
                println(response.body())
                Toast.makeText(baseContext, getString(R.string.txt_pedido_registrado), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Pedido>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

//    fun showFormEntregador(component: View) {
//        if (rl_criar_entregador.visibility == View.GONE){
//            rl_criar_entregador.visibility = View.VISIBLE
//            sv_entregadores.visibility = View.GONE
//            bt_add_entregador.visibility = View.GONE
//        } else {
//            rl_criar_entregador.visibility = View.GONE
//            sv_entregadores.visibility = View.VISIBLE
//            bt_add_entregador.visibility = View.VISIBLE
//        }
//    }

    fun tabPedidoRegistrado(component: View) {
        if (ll_pedido_registrado.visibility == View.GONE) {
            ll_pedido_registrado.visibility = View.VISIBLE
            icon_tab_pedido_registrado.setImageResource(R.drawable.ic_tab_open)
        } else {
            ll_pedido_registrado.visibility = View.GONE
            icon_tab_pedido_registrado.setImageResource(R.drawable.ic_tab_closed)
        }
    }
    fun tabPedidoEmAndamento(component: View) {
        if (ll_pedido_em_andamento.visibility == View.GONE) {
            ll_pedido_em_andamento.visibility = View.VISIBLE
            icon_tab_em_andamento.setImageResource(R.drawable.ic_tab_open)
        } else {
            ll_pedido_em_andamento.visibility = View.GONE
            icon_tab_em_andamento.setImageResource(R.drawable.ic_tab_closed)
        }
    }
    fun tabPedidoEntregue(component: View) {
        if (ll_pedido_entregue.visibility == View.GONE) {
            ll_pedido_entregue.visibility = View.VISIBLE
            icon_tab_entregue.setImageResource(R.drawable.ic_tab_open)
        } else {
            ll_pedido_entregue.visibility = View.GONE
            icon_tab_entregue.setImageResource(R.drawable.ic_tab_closed)
        }
    }
    fun tabPedidoCancelado(component: View) {
        if (ll_pedido_cancelado.visibility == View.GONE) {
            ll_pedido_cancelado.visibility = View.VISIBLE
            icon_tab_cancelado.setImageResource(R.drawable.ic_tab_open)
        } else {
            ll_pedido_cancelado.visibility = View.GONE
            icon_tab_cancelado.setImageResource(R.drawable.ic_tab_closed)
        }
    }
}