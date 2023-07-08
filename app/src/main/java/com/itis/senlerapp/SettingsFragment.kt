package com.itis.senlerapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.itis.senlerapp.databinding.FragmentSettingsBinding
import com.itis.senlerapp.db.DbManager
import com.itis.senlerapp.db.Settings
import androidx.appcompat.widget.AppCompatButton

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var binding : FragmentSettingsBinding? = null;
    private var dbManager : DbManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (dbManager == null) dbManager = DbManager(view.context)

        binding = FragmentSettingsBinding.bind(view)

        binding?.btnSave?.setOnClickListener {
            onClickSaveData(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null;
    }

    fun onClickSaveData(v: View) {
        dbManager?.open()
        Log.e("RESULT", dbManager?.readConfig().toString())

        dbManager?.writeConfig(
            binding?.tietTgToken?.text.toString(),
            binding?.tietTgGroupId?.text.toString(),
            binding?.tietVkToken?.text.toString(),
            binding?.tietVkGroupId?.text.toString(),
            binding?.tietInstToken?.text.toString()
        )

        Log.e("RESULT", dbManager?.readConfig().toString())
        dbManager?.close()
    }

}