package rakki.sme.invoice.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import rakki.sme.invoice.data.model.Customer
import rakki.sme.invoice.data.repository.CutomerRepo
import javax.inject.Inject

class CustomerDeleteUseCase @Inject constructor(val customerRepo: CutomerRepo) {
    suspend operator fun invoke(customer: Customer): Flow<Boolean> = withContext(Dispatchers.IO) {
        customerRepo.deleteRecord(customer)
        channelFlow { send(true) }
    }
}