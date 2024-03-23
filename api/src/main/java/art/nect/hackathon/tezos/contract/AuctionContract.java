package art.nect.hackathon.tezos.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import io.reactivex.Flowable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.5.2.
 */
@SuppressWarnings("rawtypes")
public class AuctionContract extends Contract {

	public static final String BINARY = "";

	public static final String FUNC_AUCTIONS = "auctions";

	public static final String FUNC_BID = "bid";

	public static final String FUNC_BIDVIRTUALLY = "bidVirtually";

	public static final String FUNC_CLAIMCOIN = "claimCoin";

	public static final String FUNC_CLAIMNFT = "claimNft";

	public static final String FUNC_CREATE = "create";

	public static final String FUNC_NFTCONTRACT = "nftContract";

	public static final String FUNC_ONERC721RECEIVED = "onERC721Received";

	public static final String FUNC_OWNER = "owner";

	public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

	public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

	public static final Event AUCTIONCREATED_EVENT = new Event("AuctionCreated",
		Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));;

	public static final Event BIDPLACED_EVENT = new Event("BidPlaced",
		Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));;

	public static final Event COINCLAIMED_EVENT = new Event("CoinClaimed",
		Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));;

	public static final Event NFTCLAIMED_EVENT = new Event("NFTClaimed",
		Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));;

	public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
		Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));;

	protected static final HashMap<String, String> _addresses;

	static {
		_addresses = new HashMap<String, String>();
	}

	@Deprecated
	protected AuctionContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
	}

	protected AuctionContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
		super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
	}

	@Deprecated
	protected AuctionContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
		super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
	}

	protected AuctionContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
	}

	public static List<AuctionCreatedEventResponse> getAuctionCreatedEvents(TransactionReceipt transactionReceipt) {
		List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(AUCTIONCREATED_EVENT, transactionReceipt);
		ArrayList<AuctionCreatedEventResponse> responses = new ArrayList<AuctionCreatedEventResponse>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
			responses.add(typedResponse);
		}
		return responses;
	}

	public static AuctionCreatedEventResponse getAuctionCreatedEventFromLog(Log log) {
		Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(AUCTIONCREATED_EVENT, log);
		AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
		typedResponse.log = log;
		typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
		return typedResponse;
	}

	public Flowable<AuctionCreatedEventResponse> auctionCreatedEventFlowable(EthFilter filter) {
		return web3j.ethLogFlowable(filter).map(log -> getAuctionCreatedEventFromLog(log));
	}

	public Flowable<AuctionCreatedEventResponse> auctionCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(AUCTIONCREATED_EVENT));
		return auctionCreatedEventFlowable(filter);
	}

	public static List<BidPlacedEventResponse> getBidPlacedEvents(TransactionReceipt transactionReceipt) {
		List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BIDPLACED_EVENT, transactionReceipt);
		ArrayList<BidPlacedEventResponse> responses = new ArrayList<BidPlacedEventResponse>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			BidPlacedEventResponse typedResponse = new BidPlacedEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
			typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(1).getValue();
			typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
			typedResponse.isVirtual = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
			responses.add(typedResponse);
		}
		return responses;
	}

	public static BidPlacedEventResponse getBidPlacedEventFromLog(Log log) {
		Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BIDPLACED_EVENT, log);
		BidPlacedEventResponse typedResponse = new BidPlacedEventResponse();
		typedResponse.log = log;
		typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
		typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(1).getValue();
		typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
		typedResponse.isVirtual = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
		return typedResponse;
	}

	public Flowable<BidPlacedEventResponse> bidPlacedEventFlowable(EthFilter filter) {
		return web3j.ethLogFlowable(filter).map(log -> getBidPlacedEventFromLog(log));
	}

	public Flowable<BidPlacedEventResponse> bidPlacedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(BIDPLACED_EVENT));
		return bidPlacedEventFlowable(filter);
	}

	public static List<CoinClaimedEventResponse> getCoinClaimedEvents(TransactionReceipt transactionReceipt) {
		List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(COINCLAIMED_EVENT, transactionReceipt);
		ArrayList<CoinClaimedEventResponse> responses = new ArrayList<CoinClaimedEventResponse>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			CoinClaimedEventResponse typedResponse = new CoinClaimedEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
			typedResponse.isVirtual = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
			responses.add(typedResponse);
		}
		return responses;
	}

	public static CoinClaimedEventResponse getCoinClaimedEventFromLog(Log log) {
		Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(COINCLAIMED_EVENT, log);
		CoinClaimedEventResponse typedResponse = new CoinClaimedEventResponse();
		typedResponse.log = log;
		typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
		typedResponse.isVirtual = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
		return typedResponse;
	}

	public Flowable<CoinClaimedEventResponse> coinClaimedEventFlowable(EthFilter filter) {
		return web3j.ethLogFlowable(filter).map(log -> getCoinClaimedEventFromLog(log));
	}

	public Flowable<CoinClaimedEventResponse> coinClaimedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(COINCLAIMED_EVENT));
		return coinClaimedEventFlowable(filter);
	}

	public static List<NFTClaimedEventResponse> getNFTClaimedEvents(TransactionReceipt transactionReceipt) {
		List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NFTCLAIMED_EVENT, transactionReceipt);
		ArrayList<NFTClaimedEventResponse> responses = new ArrayList<NFTClaimedEventResponse>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			NFTClaimedEventResponse typedResponse = new NFTClaimedEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
			responses.add(typedResponse);
		}
		return responses;
	}

	public static NFTClaimedEventResponse getNFTClaimedEventFromLog(Log log) {
		Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NFTCLAIMED_EVENT, log);
		NFTClaimedEventResponse typedResponse = new NFTClaimedEventResponse();
		typedResponse.log = log;
		typedResponse.auctionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
		return typedResponse;
	}

	public Flowable<NFTClaimedEventResponse> nFTClaimedEventFlowable(EthFilter filter) {
		return web3j.ethLogFlowable(filter).map(log -> getNFTClaimedEventFromLog(log));
	}

	public Flowable<NFTClaimedEventResponse> nFTClaimedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(NFTCLAIMED_EVENT));
		return nFTClaimedEventFlowable(filter);
	}

	public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
		List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
		ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
			typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
			responses.add(typedResponse);
		}
		return responses;
	}

	public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
		Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
		OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
		typedResponse.log = log;
		typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
		typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
		return typedResponse;
	}

	public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
		return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
	}

	public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
		return ownershipTransferredEventFlowable(filter);
	}

	public RemoteFunctionCall<Tuple9<String, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean, Boolean>> auctions(BigInteger param0) {
		final Function function = new Function(FUNC_AUCTIONS,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
			Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
		return new RemoteFunctionCall<Tuple9<String, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean, Boolean>>(function,
			new Callable<Tuple9<String, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean, Boolean>>() {

				@Override
				public Tuple9<String, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean, Boolean> call() throws Exception {
					List<Type> results = executeCallMultipleValueReturn(function);
					return new Tuple9<String, BigInteger, BigInteger, String, String, BigInteger, Boolean, Boolean, Boolean>(
						(String) results.get(0).getValue(),
						(BigInteger) results.get(1).getValue(),
						(BigInteger) results.get(2).getValue(),
						(String) results.get(3).getValue(),
						(String) results.get(4).getValue(),
						(BigInteger) results.get(5).getValue(),
						(Boolean) results.get(6).getValue(),
						(Boolean) results.get(7).getValue(),
						(Boolean) results.get(8).getValue());
				}

			});
	}

	public RemoteFunctionCall<TransactionReceipt> bid(BigInteger auctionId, BigInteger value) {
		final Function function = new Function(
			FUNC_BID,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(auctionId),
				new org.web3j.abi.datatypes.generated.Uint256(value)),
			Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> bidVirtually(BigInteger auctionId, String bidder, BigInteger value) {
		final Function function = new Function(
			FUNC_BIDVIRTUALLY,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(auctionId),
				new org.web3j.abi.datatypes.Address(bidder),
				new org.web3j.abi.datatypes.generated.Uint256(value)),
			Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> claimCoin(BigInteger auctionId) {
		final Function function = new Function(
			FUNC_CLAIMCOIN,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(auctionId)),
			Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> claimNft(BigInteger auctionId) {
		final Function function = new Function(
			FUNC_CLAIMNFT,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(auctionId)),
			Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> create(BigInteger tokenId, String coinContract, BigInteger deadline) {
		final Function function = new Function(
			FUNC_CREATE,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tokenId),
				new org.web3j.abi.datatypes.Address(coinContract),
				new org.web3j.abi.datatypes.generated.Uint256(deadline)),
			Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<String> nftContract() {
		final Function function = new Function(FUNC_NFTCONTRACT,
			Arrays.<Type>asList(),
			Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
		return executeRemoteCallSingleValueReturn(function, String.class);
	}

	public RemoteFunctionCall<byte[]> onERC721Received(String param0, String param1, BigInteger param2, byte[] param3) {
		final Function function = new Function(FUNC_ONERC721RECEIVED,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0),
				new org.web3j.abi.datatypes.Address(param1),
				new org.web3j.abi.datatypes.generated.Uint256(param2),
				new org.web3j.abi.datatypes.DynamicBytes(param3)),
			Arrays.<TypeReference<?>>asList(new TypeReference<Bytes4>() {}));
		return executeRemoteCallSingleValueReturn(function, byte[].class);
	}

	public RemoteFunctionCall<String> owner() {
		final Function function = new Function(FUNC_OWNER,
			Arrays.<Type>asList(),
			Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
		return executeRemoteCallSingleValueReturn(function, String.class);
	}

	public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
		final Function function = new Function(
			FUNC_RENOUNCEOWNERSHIP,
			Arrays.<Type>asList(),
			Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
		final Function function = new Function(
			FUNC_TRANSFEROWNERSHIP,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)),
			Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	@Deprecated
	public static AuctionContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		return new AuctionContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
	}

	@Deprecated
	public static AuctionContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
		return new AuctionContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
	}

	public static AuctionContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
		return new AuctionContract(contractAddress, web3j, credentials, contractGasProvider);
	}

	public static AuctionContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		return new AuctionContract(contractAddress, web3j, transactionManager, contractGasProvider);
	}

	public static RemoteCall<AuctionContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String nftContract_) {
		String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(nftContract_)));
		return deployRemoteCall(AuctionContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
	}

	public static RemoteCall<AuctionContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String nftContract_) {
		String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(nftContract_)));
		return deployRemoteCall(AuctionContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
	}

	@Deprecated
	public static RemoteCall<AuctionContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String nftContract_) {
		String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(nftContract_)));
		return deployRemoteCall(AuctionContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
	}

	@Deprecated
	public static RemoteCall<AuctionContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String nftContract_) {
		String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(nftContract_)));
		return deployRemoteCall(AuctionContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
	}

	protected String getStaticDeployedAddress(String networkId) {
		return _addresses.get(networkId);
	}

	public static String getPreviouslyDeployedAddress(String networkId) {
		return _addresses.get(networkId);
	}

	public static class AuctionCreatedEventResponse extends BaseEventResponse {

		public BigInteger auctionId;

	}

	public static class BidPlacedEventResponse extends BaseEventResponse {

		public BigInteger auctionId;

		public String bidder;

		public BigInteger value;

		public Boolean isVirtual;

	}

	public static class CoinClaimedEventResponse extends BaseEventResponse {

		public BigInteger auctionId;

		public Boolean isVirtual;

	}

	public static class NFTClaimedEventResponse extends BaseEventResponse {

		public BigInteger auctionId;

	}

	public static class OwnershipTransferredEventResponse extends BaseEventResponse {

		public String previousOwner;

		public String newOwner;

	}

}
