<template>
	<v-container>
		<v-row>
			<v-col cols="12" md="9" sm="6" align="center">
				<v-img width="50%" cover
					src="https://media.mkpcdn.com/prod/1000x/92f220c9071a3c910baf1d67e625c1ff_595344.jpg"></v-img>

			</v-col>

			<v-col cols="12" md="3" sm="6" outlined>
				<v-card width="100%" height="100%">
					<v-card-title class="text-h4 text-wrap">{{ NFTTitle }}</v-card-title>

					<v-card-text>
						<v-row>
							<v-col>
								<p>by <strong>{{ authorName }}</strong></p>
							</v-col>
						</v-row>


						<v-row>
							<v-col>Current Bid</v-col>
							<v-col align="right"><strong>{{ currency }}{{ currentBid }}</strong></v-col>
						</v-row>

						<v-row>
							<v-col>Owned by</v-col>
							<v-col align="right"><strong>{{ ownerName }}</strong></v-col>
						</v-row>

						<v-row>
							<v-col>
								<v-btn block variant="outlined" rounded>
									Make an offer
								</v-btn>
							</v-col>
						</v-row>
					</v-card-text>
					<v-divider class="mx-5" thickness="2"></v-divider>
					<v-card-text>
						<v-row>
							<v-col cols="auto">
								<v-avatar image="https://thispersondoesnotexist.com/" size="80"></v-avatar>
							</v-col>
							<v-col class="d-flex align-center">
								<p class="text-h6"><strong>{{ authorName }}</strong></p>
							</v-col>
						</v-row>
					</v-card-text>
					<v-divider class="mx-5" thickness="2"></v-divider>
					<v-card-text>
						<p class="text-h6"><strong>Description</strong></p>
						<ExpendableTextView :text="descriptionText"></ExpendableTextView>
					</v-card-text>
				</v-card>
			</v-col>
		</v-row>
	</v-container>
</template>

<script setup lang="ts">

const NFTTitle = 'In The Darkness Above She Waits';
const authorName = 'José Delbo';
const ownerName = 'Winifred';
const currency = "$";
const currentBid = "150.00";
const descriptionText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ut purus eget sapien fermentum aliquam. Nullam nec nunc nec nunc.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ut purus eget sapien fermentum aliquam. Nullam nec nunc nec nunc.";

</script>

<style>
/* Custom styles here */
</style>