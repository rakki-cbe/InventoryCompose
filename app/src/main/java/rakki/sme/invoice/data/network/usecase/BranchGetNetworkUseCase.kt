package rakki.sme.invoice.data.network.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rakki.sme.invoice.data.model.Profile
import rakki.sme.invoice.data.network.ProfileService
import javax.inject.Inject

class BranchGetNetworkUseCase @Inject constructor(val branchRepo: ProfileService) {
    suspend operator fun invoke(): Profile = withContext(Dispatchers.IO) {
        branchRepo.getProfileForCompany("test")
    }
}