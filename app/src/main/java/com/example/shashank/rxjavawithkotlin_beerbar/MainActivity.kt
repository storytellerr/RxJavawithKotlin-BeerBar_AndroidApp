package com.example.shashank.rxjavawithkotlin_beerbar

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory




class MainActivity : AppCompatActivity(), DataAdapter.Listener {

    val CUSTOM_DIALOG_ID = 0
    var dialog_TextView: TextView? = null
    var KEY_TEXTPSS = "TEXTPSS"

    override fun onItemClick(beer: ApiModel) {
       Log.d("hello",beer.name)
        Toast.makeText(this,"hello"+beer.name,Toast.LENGTH_LONG).show()

        val bundle = Bundle()
        bundle.putString(KEY_TEXTPSS,"hello this is a text")
        showDialog(CUSTOM_DIALOG_ID,bundle)
    }


    private val TAG = MainActivity::class.java.simpleName

        private val BASE_URL = "http://starlord.hackerearth.com"

        private var mCompositeDisposable: CompositeDisposable? = null

        private var mAndroidArrayList: ArrayList<ApiModel>? = null

        private var mAdapter: DataAdapter? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            mCompositeDisposable = CompositeDisposable()

            initRecyclerView()

            loadJSON()
        }

        private fun initRecyclerView() {

            rv_list.setHasFixedSize(true)
            val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
            rv_list.layoutManager = layoutManager
        }

        private fun loadJSON() {

            val requestInterface = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RequestInterface::class.java)

            mCompositeDisposable?.add(requestInterface.getData()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError))

        }

        private fun handleResponse(androidList: List<ApiModel>) {

            mAndroidArrayList = ArrayList(androidList)
            mAdapter = DataAdapter(mAndroidArrayList!!, this)

            rv_list.adapter = mAdapter
        }

        private fun handleError(error: Throwable) {

            Log.d(TAG, error.localizedMessage)

            Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
        }



        override fun onDestroy() {
            super.onDestroy()
            mCompositeDisposable?.clear()
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("hello","cart")
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateDialog(id: Int): Dialog? {

        var dialog: Dialog? = null

        when (id) {
            CUSTOM_DIALOG_ID -> {
                dialog = Dialog(this)
                dialog.setContentView(R.layout.custom_dialog)
                dialog.setTitle("Custom Dialog")
                dialog_TextView = dialog.findViewById<View>(R.id.dialogtext) as TextView

                val dialog_OK = dialog.findViewById<View>(R.id.dialog_ok) as Button
                dialog_OK.setOnClickListener(View.OnClickListener() {

                    fun onClick(v: View) {
                        // TODO Auto-generated method stub
                        Toast.makeText(this,
                                "Dismiss by OK button",
                                Toast.LENGTH_LONG).show()
                        dismissDialog(CUSTOM_DIALOG_ID)
                    }
                })

                val dialog_Cancel = dialog.findViewById<View>(R.id.dialog_cancel) as Button
                dialog_Cancel.setOnClickListener(View.OnClickListener() {

                    fun onClick(v: View) {
                        // TODO Auto-generated method stub
                        Toast.makeText(this,
                                "Dismiss by Cancel button",
                                Toast.LENGTH_LONG).show()
                        dismissDialog(CUSTOM_DIALOG_ID)
                    }
                })
            }
        }

        return dialog
    }

    override fun onPrepareDialog(id: Int, dialog: Dialog, bundle: Bundle) {
        // TODO Auto-generated method stub
        super.onPrepareDialog(id, dialog, bundle)

        when (id) {
            CUSTOM_DIALOG_ID -> dialog_TextView?.setText("Text passed to Dialog: " + bundle.getString(KEY_TEXTPSS)!!)
        }

    }
}