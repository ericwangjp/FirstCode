package com.example.firstcode

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.firstcode.databinding.ActivityContentResolverBinding

class ContentResolverActivity : AppCompatActivity() {
    private val contactsList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var activityContentResolverBinding: ActivityContentResolverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContentResolverBinding = ActivityContentResolverBinding.inflate(layoutInflater)
        setContentView(activityContentResolverBinding.root)
        initData()
    }

    private fun initData() {
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactsList)
        activityContentResolverBinding.listView.adapter = adapter
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 1)
        } else {
            readContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts()
                } else {
                    Toast.makeText(this, "你拒绝了权限", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun readContacts() {
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.apply {
            while (moveToNext()) {
                val name =
                    getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactsList.add("$name\n$number")
            }
            adapter.notifyDataSetChanged()
            close()
        }
    }
}