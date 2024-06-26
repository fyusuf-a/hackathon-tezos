package art.nect.hackathon.tezos.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class USDCToken extends Contract {

	public static final String BINARY = "";

	public static final String FUNC_ALLOWANCE = "allowance";

	public static final String FUNC_APPROVE = "approve";

	public static final String FUNC_BALANCEOF = "balanceOf";

	public static final String FUNC_DECIMALS = "decimals";

	public static final String FUNC_MINT = "mint";

	public static final String FUNC_NAME = "name";

	public static final String FUNC_OWNER = "owner";

	public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

	public static final String FUNC_SYMBOL = "symbol";

	public static final String FUNC_TOTALSUPPLY = "totalSupply";

	public static final String FUNC_TRANSFER = "transfer";

	public static final String FUNC_TRANSFERFROM = "transferFrom";

	public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

	public static final Event APPROVAL_EVENT = new Event("Approval",
		Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));;

	public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
		Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));;

	public static final Event TRANSFER_EVENT = new Event("Transfer",
		Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));;

	protected static final HashMap<String, String> _addresses;

	static {
		_addresses = new HashMap<String, String>();
	}

	@Deprecated
	protected USDCToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
	}

	protected USDCToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
		super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
	}

	@Deprecated
	protected USDCToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
		super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
	}

	protected USDCToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
	}

	public static List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
		List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
		ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			ApprovalEventResponse typedResponse = new ApprovalEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
			typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
			typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
			responses.add(typedResponse);
		}
		return responses;
	}

	public static ApprovalEventResponse getApprovalEventFromLog(Log log) {
		Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
		ApprovalEventResponse typedResponse = new ApprovalEventResponse();
		typedResponse.log = log;
		typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
		typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
		typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
		return typedResponse;
	}

	public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
		return web3j.ethLogFlowable(filter).map(log -> getApprovalEventFromLog(log));
	}

	public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
		return approvalEventFlowable(filter);
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

	public static List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
		List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
		ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			TransferEventResponse typedResponse = new TransferEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
			typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
			typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
			responses.add(typedResponse);
		}
		return responses;
	}

	public static TransferEventResponse getTransferEventFromLog(Log log) {
		Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
		TransferEventResponse typedResponse = new TransferEventResponse();
		typedResponse.log = log;
		typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
		typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
		typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
		return typedResponse;
	}

	public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
		return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
	}

	public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
		return transferEventFlowable(filter);
	}

	public RemoteFunctionCall<BigInteger> allowance(String owner, String spender) {
		final Function function = new Function(FUNC_ALLOWANCE,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(owner),
				new org.web3j.abi.datatypes.Address(spender)),
			Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public RemoteFunctionCall<TransactionReceipt> approve(String spender, BigInteger value) {
		final Function function = new Function(
			FUNC_APPROVE,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(spender),
				new org.web3j.abi.datatypes.generated.Uint256(value)),
			Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<BigInteger> balanceOf(String account) {
		final Function function = new Function(FUNC_BALANCEOF,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)),
			Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public RemoteFunctionCall<BigInteger> decimals() {
		final Function function = new Function(FUNC_DECIMALS,
			Arrays.<Type>asList(),
			Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public RemoteFunctionCall<TransactionReceipt> mint(String to, BigInteger amount) {
		final Function function = new Function(
			FUNC_MINT,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(to),
				new org.web3j.abi.datatypes.generated.Uint256(amount)),
			Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<String> name() {
		final Function function = new Function(FUNC_NAME,
			Arrays.<Type>asList(),
			Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
		return executeRemoteCallSingleValueReturn(function, String.class);
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

	public RemoteFunctionCall<String> symbol() {
		final Function function = new Function(FUNC_SYMBOL,
			Arrays.<Type>asList(),
			Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
		return executeRemoteCallSingleValueReturn(function, String.class);
	}

	public RemoteFunctionCall<BigInteger> totalSupply() {
		final Function function = new Function(FUNC_TOTALSUPPLY,
			Arrays.<Type>asList(),
			Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public RemoteFunctionCall<TransactionReceipt> transfer(String to, BigInteger value) {
		final Function function = new Function(
			FUNC_TRANSFER,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(to),
				new org.web3j.abi.datatypes.generated.Uint256(value)),
			Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to, BigInteger value) {
		final Function function = new Function(
			FUNC_TRANSFERFROM,
			Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(from),
				new org.web3j.abi.datatypes.Address(to),
				new org.web3j.abi.datatypes.generated.Uint256(value)),
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
	public static USDCToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		return new USDCToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
	}

	@Deprecated
	public static USDCToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
		return new USDCToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
	}

	public static USDCToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
		return new USDCToken(contractAddress, web3j, credentials, contractGasProvider);
	}

	public static USDCToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		return new USDCToken(contractAddress, web3j, transactionManager, contractGasProvider);
	}

	public static RemoteCall<USDCToken> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
		return deployRemoteCall(USDCToken.class, web3j, credentials, contractGasProvider, BINARY, "");
	}

	public static RemoteCall<USDCToken> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		return deployRemoteCall(USDCToken.class, web3j, transactionManager, contractGasProvider, BINARY, "");
	}

	@Deprecated
	public static RemoteCall<USDCToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		return deployRemoteCall(USDCToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
	}

	@Deprecated
	public static RemoteCall<USDCToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
		return deployRemoteCall(USDCToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
	}

	protected String getStaticDeployedAddress(String networkId) {
		return _addresses.get(networkId);
	}

	public static String getPreviouslyDeployedAddress(String networkId) {
		return _addresses.get(networkId);
	}

	public static class ApprovalEventResponse extends BaseEventResponse {

		public String owner;

		public String spender;

		public BigInteger value;

	}

	public static class OwnershipTransferredEventResponse extends BaseEventResponse {

		public String previousOwner;

		public String newOwner;

	}

	public static class TransferEventResponse extends BaseEventResponse {

		public String from;

		public String to;

		public BigInteger value;

	}

}
