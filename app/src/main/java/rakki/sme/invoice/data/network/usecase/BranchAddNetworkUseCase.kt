package rakki.sme.invoice.data.network.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rakki.sme.invoice.data.model.Profile
import rakki.sme.invoice.data.network.ProfileService
import javax.inject.Inject

class BranchAddNetworkUseCase @Inject constructor(val branchRepo: ProfileService) {
    suspend operator fun invoke(profile: Profile): Profile = withContext(Dispatchers.IO) {
        branchRepo.saveProfile(profile)
    }


}