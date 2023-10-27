package com.example.a020_viewmodel

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a020_viewmodel.data.DataForm
import com.example.a020_viewmodel.data.DataSource.jenis
import com.example.a020_viewmodel.ui.theme._020_ViewModelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _020_ViewModelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TampilLayout()
                }
            }
        }
    }
}
@Composable
fun TextHasil(jenisnya: String,status: String, alamat: String, email: String ){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Jenis Kelamin : " + jenisnya,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )
        Text(
            text = "Status : " + status,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(text = "Alamat : " + alamat,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
        Text(text = "Email: " + email,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _020_ViewModelTheme {
        TampilLayout()
    }
}

@Composable
fun TampilLayout(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            TampilForm()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilForm(cobaViewModel : CobaViewModel = viewModel()) {
    var textNama by remember{mutableStateOf("")}
    var textTlp by remember{mutableStateOf("")}
    var textEmail by remember{ mutableStateOf("") }

    val context = LocalContext.current
    val dataForm: DataForm
    val uiState by cobaViewModel.uiState.collectAsState()
    dataForm = uiState

    OutlinedTextField(
        value = textNama, singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Nama Lengkap")},
        onValueChange = {
            textNama = it
        }
    )
    OutlinedTextField(
        value = textTlp,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Telepon")},
        onValueChange = {
            textTlp = it
        }
    )
    OutlinedTextField(
        value = textEmail, singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Email")},
        onValueChange = {
            textEmail = it
        }
    )
    SelectJK(
        options = jenis.map { id -> context.resources.getString(id) },
        onSelectionChanged = {cobaViewModel.setJenisK(it)}
    )
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            cobaViewModel.insertData(textNama, textTlp, textEmail, dataForm.sex)
        }
    ) {
        Text(text = stringResource(R.string.submit),
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(180.dp))
    TextHasil(
        jenisnya = cobaViewModel.jenisKl,
        status = cobaViewModel.status,
        alamat = cobaViewModel.alamat,
        email = cobaViewModel.email,
    )

}
@Composable
fun SelectJK(
    options: List<String>,
    onSelectionChanged: (String) -> Unit={}
){
    var selectedValue by rememberSaveable{ mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        options.forEach {item ->
            Row(
                modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                )
                Text(item)
            }
        }
    }
}
