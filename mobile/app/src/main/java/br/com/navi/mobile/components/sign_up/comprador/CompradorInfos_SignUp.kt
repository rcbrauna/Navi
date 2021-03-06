package br.com.navi.mobile.components.sign_up.comprador

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.navi.mobile.R
import br.com.navi.mobile.components.SelectUserType_SignUp
import br.com.navi.mobile.models.Comprador
import kotlinx.android.synthetic.main.activity_comprador_infos_sign_up.*

class CompradorInfos_SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprador_infos_sign_up)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, SelectUserType_SignUp::class.java))
    }

    private var comprador : Comprador = Comprador(null, "", "", "", "", "", null)

    fun backToSelectUserType(component : View) {
        val previousScreen = Intent(this, SelectUserType_SignUp::class.java)
        startActivity(previousScreen)
    }

    fun addCompradorInfos(component: View) {
        val compradorOtherInfosActivity = Intent(this, CompradorOtherInfos_SignUp::class.java)

        this.comprador.nome = et_compradorNome.text.toString()
        this.comprador.email = et_compradorEmail.text.toString()
        this.comprador.cpf = et_compradorCpf.text.toString()

//        val call = ApiAccessUtils().compradorService().getComprador(this.comprador.cpf)
//        call.enqueue(object : Callback<Comprador> {
//            override fun onResponse(call: Call<Comprador>,
//                                    response: Response<Comprador>) {
//                if (response.isSuccessful && response.code() == 200) {
//                    Toast.makeText(applicationContext, "Esse CPF já foi cadastrado!", Toast.LENGTH_SHORT).show()
//                    // Bloquear botão "Proximo"
//                }
//                else if (response.isSuccessful && response.code() == 404) {
//                    // Não sei o que fazer aqui :P
//
//                }
//            }
//
//            override fun onFailure(call: Call<Comprador?>?,
//                                   t: Throwable?) {
//                Log.e("onFailure error", t?.message)
//            }
//        })


        compradorOtherInfosActivity.putExtra("nome", this.comprador.nome)
        compradorOtherInfosActivity.putExtra("email", this.comprador.email)
        compradorOtherInfosActivity.putExtra("senha", this.comprador.senha)
        compradorOtherInfosActivity.putExtra("telefone", this.comprador.telefone)
        compradorOtherInfosActivity.putExtra("cpf", this.comprador.cpf)

        startActivity(Intent(this, CompradorOtherInfos_SignUp::class.java))
    }

}