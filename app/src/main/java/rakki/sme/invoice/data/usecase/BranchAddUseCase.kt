package rakki.sme.invoice.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import rakki.sme.invoice.data.model.Profile
import rakki.sme.invoice.data.repository.BranchRepo
import javax.inject.Inject

class BranchAddUseCase @Inject constructor(val customerRepo: BranchRepo) {
    suspend operator fun invoke(branch: Profile): Flow<Boolean> = withContext(Dispatchers.IO) {
        customerRepo.saveCustomerData(branch)
        channelFlow { send(true) }
    }
}