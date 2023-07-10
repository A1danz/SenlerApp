package com.itis.senlerapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.itis.senlerapp.databinding.FragmentSettingsBinding
import com.itis.senlerapp.db.DbManager
import com.itis.senlerapp.db.Settings
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import kotlin.reflect.typeOf

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var binding : FragmentSettingsBinding? = null;
    private var dbManager : DbManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (dbManager == null) {
            dbManager = DbManager(view.context)
        }
        binding = FragmentSettingsBinding.bind(view)

        binding?.btnSave?.setOnClickListener {
            onClickSaveData(it)
        }

        binding?.btnInstruction?.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_instructionFragment)
        }

        fillConfigFields()
    }

    override fun onDestroy() {
        fillConfigFields()
        super.onDestroy()
        binding = null;
        dbManager = null;
    }

    override fun onDestroyView() {
        fillConfigFields()
        super.onDestroyView()
    }

    fun onClickSaveData(v: View) {
        dbManager!!.open()
        Log.e("RESULT", dbManager?.readConfig().toString())
        dbManager?.writeConfig(
            binding?.tietTgToken?.text.toString(),
            binding?.tietTgGroupId?.text.toString(),
            binding?.tietVkToken?.text.toString(),
            binding?.tietVkGroupId?.text.toString(),
            binding?.tietInstToken?.text.toString()
        )

        Log.e("RESULT", dbManager?.readConfig().toString())
        dbManager!!.close()
    }

    fun getConfig() : HashMap<String, String>? {
        dbManager!!.open()
        var result = dbManager?.readConfig()
        dbManager!!.close()
        return result;
    }

    fun fillConfigFields() {
        dbManager!!.open()
        val map = getConfig()

        binding?.tietTgToken!!.setText(map!!.get(Settings.COLUMN_NAME_TG_TOKEN).toString())
        binding?.tietTgGroupId?.setText(map.get(Settings.COLUMN_NAME_TG_GROUP_ID).toString())
        binding?.tietVkToken?.setText(map.get(Settings.COLUMN_NAME_VK_GROUP_ID).toString())
        binding?.tietVkGroupId?.setText(map.get(Settings.COLUMN_NAME_VK_GROUP_ID).toString())
        binding?.tietInstToken?.setText(map.get(Settings.COLUMN_NAME_INST_TOKEN).toString())
        dbManager!!.close()

    }

}