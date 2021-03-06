package br.com.navi.mobile.components.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.navi.mobile.R
import br.com.navi.mobile.components.SelectUserType_SignUp
import br.com.navi.mobile.components.comprador.MainComprador
import br.com.navi.mobile.components.entregador.MainEntregador
import br.com.navi.mobile.components.vendedor.MainVendedor
import br.com.navi.mobile.models.Comprador
import br.com.navi.mobile.models.Entregador
import br.com.navi.mobile.models.Vendedor
import br.com.navi.mobile.services.CompradorService
import br.com.navi.mobile.services.EntregadorService
import br.com.navi.mobile.services.VendedorService
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var codUser = ""
var nomeUser = ""
var emailUser = ""
var cnhUser = ""

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        carregandoGif()

        et_registre.setOnClickListener {
            startActivity(Intent(this, SelectUserType_SignUp::class.java))
        }
        bt_proximo.setOnClickListener { logando() }
    }

    fun logando(){
        var autenticou = false

        // colocando o loading
        bt_proximo.visibility = View.GONE
        loading.visibility = View.VISIBLE

        // guardando os valores dos campos em uma variavel
        val user = et_email.text.toString()
        val senha = et_senha.text.toString()

        // preparando a consulta na API
        val retrofit = Retrofit.Builder()
            .baseUrl("https://navi--api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requestsComprador = retrofit.create(CompradorService::class.java)
        val callComprador = requestsComprador.getCompradores()

        val requestsVendedor = retrofit.create(VendedorService::class.java)
        val callVendedor = requestsVendedor.getVendedores()

        val requestsEntregador = retrofit.create(EntregadorService::class.java)
        val callEntregador = requestsEntregador.getEntregadores()

        // consultando a API
        callComprador.enqueue(object : Callback<List<Comprador>> {
            override fun onFailure(call: Call<List<Comprador>>, t: Throwable) {
            }

            // Comprador ------------------------------------------------------------------------
            override fun onResponse(
                    call: Call<List<Comprador>>,
                    response: Response<List<Comprador>>
            ) {
                response.body()?.forEach {
                    if (user == it.email && senha == it.senha) {
                        autenticou = true

                        nomeUser = it.nome
                        emailUser = it.email
                        codUser = it.cpf

                        Toast.makeText(baseContext, getString(R.string.txt_bemvindo, it.nome), Toast.LENGTH_SHORT).show()
                        openCompradorMain()
                        return
                    } else {
                        autenticou = false
                    }
                }
                if (!autenticou) {
                    callVendedor.enqueue(object : Callback<List<Vendedor>> {
                        override fun onFailure(call: Call<List<Vendedor>>, t: Throwable) {
                        }

                        // Vendedor ------------------------------------------------------------------------
                        override fun onResponse(
                                call: Call<List<Vendedor>>,
                                response: Response<List<Vendedor>>
                        ) {
                            response.body()?.forEach {
                                if (user == it.email && senha == it.senha) {
                                    autenticou = true

                                    nomeUser = it.nome
                                    emailUser = it.email
                                    codUser = it.cnpj

                                    Toast.makeText(baseContext, getString(R.string.txt_bemvindo, it.nome), Toast.LENGTH_SHORT).show()
                                    openVendedorMain()
                                    return
                                } else {
                                    autenticou = false
                                }
                            }
                            if (!autenticou) {
                                callEntregador.enqueue(object : Callback<List<Entregador>> {
                                    override fun onFailure(call: Call<List<Entregador>>, t: Throwable) {
                                    }

                                    // Entregador ------------------------------------------------------------------------
                                    override fun onResponse(
                                            call: Call<List<Entregador>>,
                                            response: Response<List<Entregador>>
                                    ) {
                                        response.body()?.forEach {
                                            if (user == it.email && senha == it.senha) {
                                                autenticou = true

                                                nomeUser = it.nome
                                                emailUser = it.email
                                                codUser = it.cpf
                                                cnhUser = it.cnh

                                                codUser = it.vendedor?.cnpj.toString()
                                                Toast.makeText(baseContext, getString(R.string.txt_bemvindo, it.nome), Toast.LENGTH_SHORT).show()
                                                openEntregadorMain()
                                                return
                                            } else {
                                                autenticou = false
                                            }
                                        }
                                        if (!autenticou) {
                                            // tirando o loading
                                            bt_proximo.visibility = View.VISIBLE
                                            loading.visibility = View.GONE

                                            Toast.makeText(baseContext, getString(R.string.txt_login_erro), Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                })
                            }
                        }
                    })
                }
            }

        })

    }

    // direciona para a tela do respectivo usuario
    private fun openCompradorMain(){
        startActivity(Intent(this, MainComprador::class.java))
    }

    private fun openVendedorMain(){
        startActivity(Intent(this, MainVendedor::class.java))
    }

    private fun openEntregadorMain(){
        startActivity(Intent(this, MainEntregador::class.java))
    }

    // função para trazer o spinner de carregamento
    fun carregandoGif() {
        val imageView: ImageView = findViewById(R.id.loading)
        Glide.with(this).load(R.drawable.carregando).into(imageView)
    }
}