package rakki.sme.invoice.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rakki.sme.invoice.data.model.Customer
import rakki.sme.invoice.data.repository.CutomerRepo
import javax.inject.Inject

class CustomerGetUseCase @Inject constructor(val customerRepo: CutomerRepo) {

    suspend operator fun invoke(): List<Customer> = withContext(Dispatchers.IO) {
        customerRepo.getAllCustomer()
    }


}